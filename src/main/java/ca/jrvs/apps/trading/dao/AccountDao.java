package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
@Repository
public class AccountDao extends JdbcCrdDao<Account,Integer> {
    private String table_name="account";
    private String id_name="id";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    public AccountDao(DataSource dataSource){
        jdbcTemplate=new JdbcTemplate(dataSource);
        simpleJdbcInsert=new SimpleJdbcInsert(dataSource).withTableName(table_name).usingGeneratedKeyColumns(id_name);
    }
    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleInsert() {
        return simpleJdbcInsert;
    }

    @Override
    public String getTableName() {
        return table_name;
    }

    @Override
    public String getIdName() {
        return id_name;
    }

    @Override
    Class getEntityClass() {
        return null;
    }
    public boolean existById(Integer id){
        return super.existById(id);
    }

    public Account findByTraderId(Integer id){
        return  super.findById(getIdName(),id,false,getEntityClass());
    }public Account findByTraderIdForUpdate(Integer id){
        return  super.findById(getIdName(),id,false,getEntityClass());
    }
    public Account amountUpdate(Integer id, Double amount){
        if (super.existById(id)) {
            String sql_query="UPDATE"+table_name+"SET amount=? WHERE id=?";
            int row=jdbcTemplate.update(sql_query,amount, id);
            if(row!=1){
                throw new IncorrectResultSizeDataAccessException(1, row);
            }
            return findById(id);
        }
        return null;
    }
}
