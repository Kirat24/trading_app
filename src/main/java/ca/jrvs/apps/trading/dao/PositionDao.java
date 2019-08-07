package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.util.List;
@Repository
public class PositionDao {

    private static final String table_name="position";
    private static final String id_name="account_id";
    private Logger logger=  LoggerFactory.getLogger(PositionDao.class);
    private JdbcTemplate jdbcTemplate;

    public PositionDao(DataSource dataSource){
        jdbcTemplate=new JdbcTemplate(dataSource);
    }
    public List<Position> findByAccountId(Integer accountId){
        String sql_query="SELECT * FROM"+table_name+"WHERE "+accountId+"=?";
        return jdbcTemplate.query(sql_query, BeanPropertyRowMapper.newInstance(Position.class),accountId);

    }
    public Long findByIdandTicker(Integer accountId, String ticker){
    String sql_query="SELECT position FROM"+table_name+"WHERE"+accountId+"=? and"+ticker+"=?";
    Long position=0L;
    position=jdbcTemplate.queryForObject(sql_query,Long.class, accountId, ticker);
    return position;

    }
}
