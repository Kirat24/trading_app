package ca.jrvs.apps.trading.dao;

public interface CrdRepository<E, ID> {

    E save(E entity);

    E findById(ID id) throws ResourceNotFoundException;

    boolean existById(ID id);

    void deleteById(ID id);


}
