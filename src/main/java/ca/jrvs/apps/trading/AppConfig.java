package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.util.StringUtil;
import ch.qos.logback.core.util.StringCollectionUtil;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class AppConfig {
    private Logger logger = LoggerFactory.getLogger(AppConfig.class);
    @Value("$.{iex.host}")
    private String iex_host;
    @Bean
    public MarketDataConfig marketDataConfig(){
        if(StringUtil.isEmpty(System.getenv("IEX_PUB_TOKEN"))|| StringUtil.isEmpty(iex_host)){
            throw new IllegalArgumentException("token or iex_host  not found");
        }
        MarketDataConfig marketDataConfig=new MarketDataConfig();
        marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));
        marketDataConfig.setHost(iex_host);
        return  marketDataConfig;
    }
    @Bean
    public DataSource dataSource(){
        String jdbcUrl;
        String user;
        String password;
        jdbcUrl=System.getenv("PSQL_URL");
        user=System.getenv("USER_SQL");
        password=System.getenv("PSQL_PSWRD");
        if(StringUtil.isEmpty(jdbcUrl,user,password)) {
            throw new IllegalArgumentException("missing data source  config information");
        }
        BasicDataSource basicDataSource=new BasicDataSource();
        basicDataSource.setDriverClassName("org.postgresql.Driver");
        basicDataSource.setUrl(jdbcUrl);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        return basicDataSource;
    }

    @Bean
    public HttpClientConnectionManager httpClientConnectionManager() {
        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager();
        connMgr.setMaxTotal(50);
        connMgr.setDefaultMaxPerRoute(50);
        return connMgr;
    }

}
