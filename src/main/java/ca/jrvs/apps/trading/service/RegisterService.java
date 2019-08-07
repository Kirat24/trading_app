package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
public class RegisterService {
    private TraderDao traderDao;
    private SecurityOrderDao securityOrderDao;
    private PositionDao positionDao;
    private AccountDao accountDao;

    @Autowired
    public RegisterService(TraderDao traderDao, SecurityOrderDao securityOrderDao, PositionDao positionDao, AccountDao accountDao) {
       this.traderDao=traderDao;
       this.positionDao=positionDao;
       this.accountDao=accountDao;
       this.securityOrderDao=securityOrderDao;
    }
    /**
     * Create a new trader and initialize a new account with 0 amount.
     * - validate user input (all fields must be non empty)
     * - create a trader
     * - create an account
     * - create, setup, and return a new traderAccountView
     *
     * @param trader trader info
     * @return traderAccountView
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException for invalid input
     */
    public TraderAccountView createTraderAndAccount(Trader trader) throws IllegalAccessException {
        Trader trader1=new Trader();
        Account  account=new Account();
        TraderAccountView view=new TraderAccountView();

        account.setAmount(0);
        if(trader.getId()==null){
            throw new IllegalArgumentException("id doesnot  exist");
        }
       if(validateInput(trader)){

           view.setAccount(account);
           view.setTrader(trader);
       }

   return view;

    }

    protected boolean validateInput(Object obj) throws IllegalAccessException {
        boolean validate=false;
        Field[] declareFields=obj.getClass().getDeclaredFields();
        for(Field f: declareFields){
            if(f.get(obj)!=null){
                return validate=true;
            }
        }return validate;

    }
    /**
     * A trader can be deleted iff no open position and no cash balance.
     * - validate traderID
     * - get trader account by traderId and check account balance
     * - get positions by accountId and check positions
     * - delete all securityOrders, account, trader (in this order)
     *
     * @param traderId
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException for invalid input
     */
    public void deleteTraderById(Integer traderId) {
        if(traderId==null){
            throw new IllegalArgumentException("Invalid traderId ");
        }
        Account account=accountDao.findByTraderId(traderId);
        Position position= (Position) positionDao.findByAccountId(account.getId());

        if(account.getAmount()!=0 && position==null){
            System.out.println("you still ahve some balance or accountId is not correct");
        }
        securityOrderDao.deleteById(traderId);
        accountDao.deleteById(traderId);
        traderDao.deleteById(traderId);

}

}
