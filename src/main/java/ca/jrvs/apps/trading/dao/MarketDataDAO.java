package ca.jrvs.apps.trading.dao;


import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.util.JsonUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class MarketDataDAO {
    private Logger logger=LoggerFactory.getLogger(MarketDataDAO.class);
    private final String BASIC_QUOTE_URL;
    private HttpClientConnectionManager httpConnectionManager;
@Autowired
public MarketDataDAO(HttpClientConnectionManager connectionManager, MarketDataConfig marketDataConfig) {
    this.httpConnectionManager = connectionManager;
    BASIC_QUOTE_URL = marketDataConfig.getHost() + "stock/market/batch?symbols=%s&types=quote&token=" + marketDataConfig.getToken();
}
public List<IexQuote> findIexQuoteByTicker(List<String> tickerList) throws ResourceNotFoundException {
    //convert list to comma separated string
    JSONArray jsonArray=new JSONArray(tickerList);
    String tickers= CDL.rowToString(jsonArray);
    String uri= String.format(BASIC_QUOTE_URL,tickers);
    logger.info("Get URI:"+ uri);
    //HTTP response body in string
    String response= executeHttpGet(uri);
    //Iex will skip invalid symbols/ticker, we need to check that
    JSONObject IexJsonObject=new JSONObject(response);
    if(IexJsonObject.length()!=tickerList.size()){
        throw new ResourceNotFoundException("invalid symbols");
    }
    //unmarshall JSONObject
    List<IexQuote> iexQuotes=new ArrayList<>();
    IexJsonObject.keys().forEachRemaining(ticker->{
        try{

            String strQuote=((JSONObject)IexJsonObject.get(ticker)).get("quote").toString();
            IexQuote iexQuote= JsonUtil.toObjectFromJson(strQuote,IexQuote.class);
            iexQuotes.add(iexQuote);
        } catch (IOException e) {
        }
    });
    return iexQuotes;
    }
public IexQuote findIexQuoteByTicker(String ticker) throws ResourceNotFoundException {
    List<IexQuote> iexQuotes=findIexQuoteByTicker(Arrays.asList(ticker));
    if(iexQuotes==null||iexQuotes.size()!=1){
        throw new DataRetrievalFailureException("no quote related to this ticker found");
    }
    return iexQuotes.get(0);
}

protected String executeHttpGet(String url){
    try(CloseableHttpClient httpClient=getHttpClient()){
        HttpGet httpGet=new HttpGet(url);
        try(CloseableHttpResponse response=httpClient.execute(httpGet)){
          switch (response.getStatusLine().getStatusCode()){
              case 200:
                  //EntityUtils toString will also close inputStream in entity
                  String body= EntityUtils.toString(response.getEntity());
                  return Optional.ofNullable(body).orElseThrow(
                          ()->new IOException("Unexpected empty http response body"));
              case 404:
                  throw new ResourceAccessException("Not found");
              default:
                  throw  new DataRetrievalFailureException("" +
                          "Unexpected Satus"+ response.getStatusLine().getStatusCode());
          }
        } }catch (IOException e) {
            throw new DataRetrievalFailureException("Unable http execution error", e);
        }
    }
private CloseableHttpClient getHttpClient(){
    return HttpClients.custom()
            .setConnectionManager(httpConnectionManager)
            //prevent connection manager to shutdown when calling httpClient.close();
            .setConnectionManagerShared(true)
            .build();

}

}
