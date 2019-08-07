package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.*;
import ca.jrvs.apps.trading.model.domain.*;
import com.sun.org.apache.xpath.internal.operations.Quo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private AccountDao accountDao;
    private SecurityOrderDao securityOrderDao;
    private QuoteDAO quoteDao;
    private PositionDao positionDao;

    @Autowired
    public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao,
                        QuoteDAO quoteDao, PositionDao positionDao) {
        this.accountDao = accountDao;
        this.securityOrderDao = securityOrderDao;
        this.quoteDao = quoteDao;
        this.positionDao = positionDao;
    }

    /**
     * Execute a market order
     *
     * - validate the order (e.g. size, and ticker)
     * - Create a securityOrder (for security_order table)
     * - Handle buy or sell order
     *   - buy order : check account balance
     *   - sell order: check position for the ticker/symbol
     *   - (please don't forget to update securityOrder.status)
     * - Save and return securityOrder
     *
     * NOTE: you will need to some helper methods (protected or private)
     *
     * @param orderDto market order
     * @return SecurityOrder from security_order table
     * @throws org.springframework.dao.DataAccessException if unable to get data from DAO
     * @throws IllegalArgumentException for invalid input
     */
    public SecurityOrder executeMarketOrder(MarketOrderDto orderDto) {
        Quote quote =quoteDao.findById(orderDto.getTicker());
        if(quote==null){
            throw new IllegalArgumentException("ticker not found");
        }
        if( orderDto.getSize() == null) {
            throw new IllegalArgumentException("order size is not correct");
        }
        SecurityOrder securityOrder=null;
        securityOrder.setAccountId(orderDto.getAccountId());
        securityOrder.setTicker(orderDto.getTicker());
        securityOrder.setSize(orderDto.getSize());
        Account account=accountDao.findByTraderIdForUpdate(orderDto.getAccountId());
        if(0 < orderDto.getSize()){
            securityOrder.setPrice((int) quote.getAskPrice());
            buyOrder(orderDto,securityOrder,account);

        }
        else {
            securityOrder.setPrice((int) quote.getBidPrice());
            sellOrder(orderDto,securityOrder,account);
        }
        return securityOrderDao.save(securityOrder);
    }
    protected void buyOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder, Account account){
        Double buyPrice= Double.valueOf(marketOrderDto.getSize()*securityOrder.getPrice());
        if(account.getAmount()>=buyPrice){
            double updateAmount=account.getAmount()-buyPrice;
            accountDao.amountUpdate(marketOrderDto.getAccountId(),updateAmount);
            System.out.println(OrderStatus.FILLED);
        }
        else{
            System.out.println(OrderStatus.CANCELED);
            System.out.println("insufficient fund");
        }


    }
    protected void sellOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder, Account account){
        Long position=positionDao.findByIdandTicker(marketOrderDto.getAccountId(),securityOrder.getTicker());
        if(position+marketOrderDto.getSize()>=0){

            Double sellPrice= Double.valueOf(marketOrderDto.getSize()*securityOrder.getPrice());
            double updateAmount=account.getAmount()+sellPrice;
            accountDao.amountUpdate(marketOrderDto.getAccountId(), updateAmount);
            System.out.println(OrderStatus.FILLED);
        }
        else {
            System.out.println(OrderStatus.CANCELED);
            System.out.println("Insufficient ticker position");
        }


    }

}

