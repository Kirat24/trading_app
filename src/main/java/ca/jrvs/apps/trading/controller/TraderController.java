package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.OrderStatus;
import ca.jrvs.apps.trading.model.domain.Trader;
import ca.jrvs.apps.trading.model.domain.TraderAccountView;
import ca.jrvs.apps.trading.service.FundTransferService;
import ca.jrvs.apps.trading.service.OrderService;
import ca.jrvs.apps.trading.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Controller
@RequestMapping("/trader")
public class TraderController {
    private RegisterService registerService;
    private FundTransferService fundTransferService;
@Autowired
  public TraderController(RegisterService rSer, FundTransferService fundSer){
    this.fundTransferService=fundSer;
    this.registerService=rSer;
}
@PostMapping(path = "/traderID/{traderId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public void delTrader(@PathVariable Integer traderId){
     try{
         registerService.deleteTraderById(traderId);
     }catch (Exception e){
         throw ResponseExceptionUtil.getResponseStatusException(e);
     }
}
@ResponseStatus(HttpStatus.CREATED)
@ResponseBody
@PostMapping(path = "/")
    public TraderAccountView createTrader(@RequestBody Trader trader){
    try
    {
      return    registerService.createTraderAndAccount(trader);

    }catch (Exception e){
        throw ResponseExceptionUtil.getResponseStatusException(e);
    }
}

@PostMapping(path = "/firstname/{firstname}/lastname{lastname}/dob/{dob}/country/{country}/email/{emmail}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public TraderAccountView createTrader(@PathVariable String firstName , @PathVariable String lastName ,
                                          @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate dob,
                                          @PathVariable String country ,
                                          @PathVariable String email ){


      try{
          Trader trader=new Trader();
          trader.setFirstName(firstName);
          trader.setLastName(lastName);
          trader.setDob(dob);
          trader.setCountry(country);
          trader.setEmail(email);
          return registerService.createTraderAndAccount(trader);
      }catch (Exception e){
          throw ResponseExceptionUtil.getResponseStatusException(e);
      }
}
 @PutMapping(path = "/deposit/traderId/{traderId}/amount/{amount}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Account deposit(@PathVariable Integer traderId, @PathVariable Double amount){
    try{
      return   fundTransferService.deposit(traderId,amount);

    }catch (Exception e){
        throw ResponseExceptionUtil.getResponseStatusException(e);
    }
 }
 @PutMapping(path = "/withdraw/traderId/{traderId}/amount/{amount}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Account withdraw(@PathVariable Integer traderId, @PathVariable Double amount){
    try{
      return   fundTransferService.withdraw(traderId,amount);

    }catch (Exception e){
        throw ResponseExceptionUtil.getResponseStatusException(e);
    }
 }

}
