package sample.bussiness;

import sample.dto.CustomerDTO;

import java.util.List;

public interface CrudBO<T,ID> extends SuperBO{

    void save(T DTO) throws Exception;
    void update(T DTO) throws Exception;
    void delete(ID id) throws Exception;
    List<T> findAll() throws Exception;
    T find(ID id) throws Exception;

}
