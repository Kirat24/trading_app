package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDAO;
import ca.jrvs.apps.trading.dao.QuoteDAO;
import ca.jrvs.apps.trading.dao.ResourceNotFoundException;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import com.sun.org.apache.xpath.internal.operations.Quo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class QuoteService {
    private QuoteDAO quoteDAO;
    private MarketDataDAO marketDataDAO;
    public QuoteService(QuoteDAO quoteDAO, MarketDataDAO marketDataDAO){
        this.quoteDAO=quoteDAO;
        this.marketDataDAO=marketDataDAO;
    }
    public static Quote buildQuoteFromIexQuote(IexQuote iexQuote){
        Quote quote=new Quote();
        quote.setAskPrice(Double.parseDouble(Optional.ofNullable(iexQuote.getIexAskPrice()).orElse("0")));
        quote.setAskSize(Integer.parseInt(Optional.ofNullable(iexQuote.getIexAskSize()).orElse("0")));
        quote.setBidPrice(Double.parseDouble(Optional.ofNullable(iexQuote.getIexBidPrice()).orElse("0")));
        quote.setBidSize(Integer.parseInt(Optional.ofNullable(iexQuote.getIexBidSize()).orElse("0")));
        quote.setLastPrice(Double.parseDouble(Optional.ofNullable(iexQuote.getLatestPrice()).orElse("0")));
        quote.setTicker(iexQuote.getSymbol());
        return quote;

    }
    /**
     * Add a list of new tickers to the quote table. Skip existing ticker(s).
     *  - Get iexQuote(s)
     *  - convert each iexQuote to Quote entity
     *  - persist the quote to db
     *
     * @param tickers a list of tickers/symbols
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException for invalid input
     */

    public void initQuote(List<String> tickers) {
        List<IexQuote> iexQuoteList =tickers.stream().map(ticker -> {
            try {
                return marketDataDAO.findIexQuoteByTicker(ticker);
            } catch (Exception e) {
                throw new ResourceNotFoundException("ticker not found", e);
            }
        }).collect(Collectors.toList());
        List<Quote> quoteList=iexQuoteList.stream().map(QuoteService::buildQuoteFromIexQuote).collect(Collectors.toList());
       quoteList.forEach
               (quote ->{if(!quoteDAO.existById(quote.getId()))
               {
                   quoteDAO.save(quote);
               }});
    }
     public void initQuote(String ticker){
        initQuote(Collections.singletonList(ticker));
    }
    /**
     * Update quote table against IEX source
     *  - get all quotes from the db
     *  - foreach ticker get iexQuote
     *  - convert iexQuote to quote entity
     *  - persist quote to db
     *
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException for invalid input
     */
    public void updateMarketDao(){
        List<Quote> quotes= quoteDAO.findAll();
        List<String> tickers=quotes.stream().map(Quote::getTicker).collect(Collectors.toList());
        List<IexQuote> iexQuoteList= marketDataDAO.findIexQuoteByTicker(tickers);
        List<Quote> quoteList=iexQuoteList.stream().map(QuoteService::buildQuoteFromIexQuote).collect(Collectors.toList());
        quoteDAO.update(quoteList);

    }

}
