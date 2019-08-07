package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Trader;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;
import javax.sql.DataSource;
@Repository
public class TraderDao implements CrdRepository<Trader, Integer>{
    private Logger logger= LoggerFactory.getLogger(TraderDao.class);
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    public static final String table_name="trader";
    public static final String Id_name="id";
    public TraderDao(DataSource dataSource){
        jdbcTemplate=new JdbcTemplate(dataSource);
        simpleJdbcInsert=new SimpleJdbcInsert(dataSource).withTableName(table_name);
    }
    @Override
    public Trader save(Trader entity) {
        SqlParameterSource sqlParameterSource=new BeanPropertySqlParameterSource(entity);
        Number intId=simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);
        return null;
    }

    @Override
    public Trader findById(Integer id) throws ResourceNotFoundException {
        if(id==null){
            throw new IllegalArgumentException("id cannot be null");
        }
        Trader trader=null;
        try{
            String sql_query="SELECT * FROM"+table_name+"WHERE"+Id_name+"=?";
            trader=jdbcTemplate.queryForObject(sql_query,BeanPropertyRowMapper.newInstance(Trader.class),id);
        }catch (EmptyResultDataAccessException e){
           logger.debug("id cannot be found", e);
        }
        return trader;
    }

    @Override
    public boolean existById(Integer id) {
        if(id==null){
            throw new IllegalArgumentException("id cannot be null");
        }
        String sql_query="SELECT COUNT(*) FROM "+table_name+"WHERE"+Id_name+"=?";
        int count=jdbcTemplate.queryForObject(sql_query,Integer.class, id);
        return count!=0;
    }

    @Override
    public void deleteById(Integer id) {
        if(id==null){
            throw new IllegalArgumentException("id cannot be null");
        }
        String del_sql_query="DELETE FROM"+table_name+"WHERE"+Id_name+"=?";
        jdbcTemplate.update(del_sql_query,id);
    }


}