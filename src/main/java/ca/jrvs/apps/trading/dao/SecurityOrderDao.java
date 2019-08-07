package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
@Repository
public class SecurityOrderDao  extends JdbcCrdDao<SecurityOrder, Integer> {
    private String table_name="securityOrder";
    private String id_name="id";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    public SecurityOrderDao(DataSource dataSource){
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
        return SecurityOrder.class;
    }

    public SecurityOrder save(SecurityOrder entity){
        return super.save(entity);
    }
    public SecurityOrder findById(Integer id){
        return super.findById(getIdName(),id,false,getEntityClass());

    }
    public void deleteById(Integer id){
         super.deleteById(getIdName(),id);
    }


}
