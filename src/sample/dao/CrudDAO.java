package sample.dao;

import sample.entity.SuperEntity;

import java.util.List;

public interface CrudDAO<T extends SuperEntity,ID> extends SuperDAO {
    T find(ID valueOF_ID) throws Exception;
    List<T> findAll() throws Exception;
    void save(T entity)throws Exception;
    void update(T entity) throws Exception;
    void delete(ID valueOF_ID) throws  Exception;
}
