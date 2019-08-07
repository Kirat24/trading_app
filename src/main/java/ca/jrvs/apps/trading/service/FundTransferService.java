package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Trader;

public class FundTransferService {
private AccountDao accountDao;
public FundTransferService(AccountDao accountDao){
    this.accountDao=accountDao;

}
    /**
     * Deposit a fund to the account which is associated with the traderId
     * - validate user input
     * - account = accountDao.findByTraderId
     * - accountDao.updateAmountById
     *
     * @param traderId trader id
     * @param fund found amount (can't be 0)
     * @return updated Account object
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException for invalid input
     */
    public Account deposit(Integer traderId, Double fund) {
        if(fund<=0||traderId==null){
            throw new IllegalArgumentException("fund cannot be 0/Invalid traderId");
        }
            Account account=accountDao.findByTraderIdForUpdate(traderId);
        Double amount=account.getAmount()+fund;
            return accountDao.amountUpdate(account.getId(),amount);
        }
    /**
     * Withdraw a fund from the account which is associated with the traderId
     *
     * - validate user input
     * - account = accountDao.findByTraderId
     * - accountDao.updateAmountById
     *
     * @param traderId trader ID
     * @param fund amount can't be 0
     * @return updated Account object
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException for invalid input
     */
    public Account withdraw(Integer traderId, Double fund) {
        if(fund<=0||traderId==null){
            throw new IllegalArgumentException("fund cannot be 0/Invalid traderId ");
        }
        Account account=accountDao.findByTraderIdForUpdate(traderId);
        Double amount=account.getAmount()-fund;
        if(amount<0){
            throw new IllegalArgumentException("after withdrawing amount<0, this transaction cannot be done");
        }
        return accountDao.amountUpdate(account.getId(),amount);

    }
}



