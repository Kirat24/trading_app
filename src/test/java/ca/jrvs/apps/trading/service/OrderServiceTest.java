package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDAO;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.model.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    @Mock
    private AccountDao accountDao;
    @Mock
    private SecurityOrderDao securityOrderDao;
    @Mock
    private QuoteDAO quoteDao;
    @Mock
    private PositionDao positionDao;
    @InjectMocks
    private OrderService orderService;
    @Captor
    ArgumentCaptor<SecurityOrder> securityOrderArgumentCaptor;
    private MarketOrderDto orderDto;
    @Before
    public void setup(){
        orderDto=new MarketOrderDto();
        orderDto.setAccountId(1);
        orderDto.setSize(1);
        orderDto.setTicker("AAPL");
    }
    @Test
    public void executeMarketOrder() {
        Quote quote=new Quote();
        quote.setAskSize(10);
        quote.setAskPrice(100.0);
        when(quoteDao.findById(orderDto.getTicker())).thenReturn(quote);
        Account account=new Account();
        account.setAmount(100);
        account.setId(orderDto.getAccountId());
        when(accountDao.findByTraderIdForUpdate(orderDto.getAccountId())).thenReturn(account);
        orderService.executeMarketOrder(orderDto);
        verify(securityOrderDao).save(securityOrderArgumentCaptor.capture());
        SecurityOrder captorOrder=securityOrderArgumentCaptor.getValue();
        assertEquals(OrderStatus.FILLED,captorOrder.getOrderStatus());

    }

   }