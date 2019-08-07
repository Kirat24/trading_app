package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import com.sun.org.apache.xalan.internal.xsltc.dom.SimpleResultTreeImpl;
import com.sun.org.apache.xpath.internal.operations.Quo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class QuoteDAO implements CrdRepository<Quote, String> {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    public static final String table_name="quote";
    public static final String Id_name="ticker";
    public QuoteDAO(DataSource dataSource){
        jdbcTemplate=new JdbcTemplate(dataSource);
        simpleJdbcInsert=new SimpleJdbcInsert(dataSource).withTableName(table_name);
    }
    @Override
    public Quote save(Quote entity) {
        SqlParameterSource  parameterSource= new BeanPropertySqlParameterSource(entity);
        Number num_id= simpleJdbcInsert.executeAndReturnKey(parameterSource);
        entity.setId(num_id);
        return entity;
}
     @Override
    public Quote findById(String id) throws ResourceNotFoundException {
         Quote quotes = null;
         if (existById(id)) {
             try {
                 String sql_query = "SELECT * FROM " + table_name + "WHERE" + Id_name + "=?";
                 try {
                     quotes = (Quote) jdbcTemplate.queryForObject(sql_query, BeanPropertyRowMapper.newInstance(Quote.class), id);

                 } catch (EmptyResultDataAccessException e) {
                     throw new ResourceNotFoundException("Resource not found");
                 }

             } catch (IllegalArgumentException e) {
                 System.out.println("id not found");
             }
         }

         return quotes;
     }
     public List<Quote> findAll(){
         String sql_query="SELECT * FROM"+table_name;
        return  jdbcTemplate.query(sql_query,BeanPropertyRowMapper.newInstance(Quote.class));

     }
     public void update(List<Quote>quotes){
        String sql_query="UPDATE"+table_name+"SET last_price=?, bid_price=?, bid_size=?, ask_price=?,ask_size=?";
        List<Object[]> batch=new ArrayList<>();
         quotes.forEach(quote -> {
             if(!existById(quote.getTicker())){
                 throw new ResourceNotFoundException("Ticker not found");
             }
             Object[] values= new Object[]{quote.getLastPrice(),quote.getBidPrice(),quote.getBidSize()
             ,quote.getAskPrice(),quote.getAskSize(),quote.getTicker()};
             batch.add(values);
         });
         int[] rows=jdbcTemplate.batchUpdate(sql_query,batch);
         int total_rows= Arrays.stream(rows).sum();
         if(total_rows!=quotes.size()){
             throw new IncorrectResultSizeDataAccessException("number of rows",quotes.size(),total_rows);
         }
    }
    @Override
    public boolean existById(String id) {
        boolean result=false;
        String sql_query= "SELECT EXISTS(SELECT *  FROM "+ table_name+"WHERE"+Id_name+"=?)";
        try{
            result =jdbcTemplate.queryForObject(sql_query,boolean.class, id);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void deleteById(String id) {
        if(id==null){
            throw new IllegalArgumentException("arguments cannot be null");
        }
        String del_sql_query="DELETE * FROM "+ table_name+"WHERE"+Id_name+"=?";
        jdbcTemplate.update(del_sql_query, id);
    }
}
