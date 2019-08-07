package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;


public  abstract class JdbcCrdDao<E extends Entity,ID >implements CrdRepository<E, ID> {
    private Logger logger = LoggerFactory.getLogger(JdbcCrdDao.class);
    abstract public JdbcTemplate getJdbcTemplate();
    abstract public SimpleJdbcInsert getSimpleInsert();
    abstract public String getTableName();
    abstract public String getIdName();
    abstract Class getEntityClass();


    @Override
    public E save(E entity) {
        SqlParameterSource sqlParameterSource=new BeanPropertySqlParameterSource(entity);
        Number int_id=getSimpleInsert().executeAndReturnKey(sqlParameterSource);
        entity.setId(int_id.intValue());
        return  entity;
    }

    @Override
    public E findById(ID id) throws ResourceNotFoundException {
        return findById(getIdName(),id,false,getEntityClass());
    }
    public E findByIdForUpdate(ID id){
        return findById(getIdName(),id,true,getEntityClass());
    }
    public E findById(String id_name,ID id, boolean for_update, Class clazz){
        E t=null;
        String sql_query="SELECT * FROM "+getTableName()+"WHERE"+id_name+"=?";
        if (for_update) {
            sql_query+= " for update";
        }
        logger.info(sql_query);
        t= (E)getJdbcTemplate().queryForObject(sql_query,BeanPropertyRowMapper.newInstance(clazz),id);
        if(t==null){
            throw new ResourceNotFoundException("resource not found");
        }
        return t;
    }


    @Override
    public boolean existById(ID id) {
        return existById(getIdName(),id);
    }
 public boolean existById(String id_name,ID id) {
        String sql_query="SELECT COUNT(*) FROM "+getTableName()+"WHERE"+id_name+"=?";
        int count=getJdbcTemplate().queryForObject(sql_query,Integer.class,id);
        return count!=0;
    }

    @Override
    public void deleteById(ID id) {
        deleteById(getIdName(),id);
    }
     public void deleteById(String id_name,ID id) {
        if(id==null){
            throw new IllegalArgumentException("id not found");
        }
        String del_sql_query="DELETE FROM "+getTableName()+"WHERE"+id_name+"=?";
        getJdbcTemplate().update(del_sql_query,id);
    }


}
