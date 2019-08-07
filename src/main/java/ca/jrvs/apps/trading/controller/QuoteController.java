package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.dao.MarketDataDAO;
import ca.jrvs.apps.trading.dao.QuoteDAO;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.service.QuoteService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/quote")
public class QuoteController {
    private MarketDataDAO marketDataDAO;
    private QuoteDAO quoteDAO;
    private QuoteService quoteSrevice;

    @Autowired
    public QuoteController(QuoteService service, QuoteDAO quoteDAO,
                           MarketDataDAO marketDataDAO) {
        this.quoteSrevice = service;
        this.quoteDAO = quoteDAO;
        this.marketDataDAO = marketDataDAO;
    }

    @GetMapping(path = "/iex/ticker/{ticker}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public IexQuote getQuote(@PathVariable String ticker) {
        try {
            return marketDataDAO.findIexQuoteByTicker(ticker);
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);

        }

    }

    @PutMapping(path = "/iexMarketData")
    @ResponseStatus(HttpStatus.OK)
    public void updateMarketData() {
        try {
            quoteSrevice.updateMarketDao();
        } catch (Exception e) {
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    @GetMapping(path = "/dailyList")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List <Quote> getDailyList(){
        try{
            return quoteDAO.findAll();
        }catch (Exception e){
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
    @PutMapping(path = "/")
     @ResponseStatus(HttpStatus.OK)
    public void updateQuote(Quote quote){
        try{
            quoteDAO.update(Collections.singletonList(quote));
        }catch (Exception e){
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
    @PostMapping(path="/tickerId/{tickerId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value= {@ApiResponse(code = 400, message = "ticker is not found in IEX system")})
    public void createQuote(@PathVariable String tickerID){
        try{
        quoteSrevice.initQuote(tickerID);
    }catch (Exception e){
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }


}








