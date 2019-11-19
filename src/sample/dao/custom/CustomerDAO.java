package sample.dao.custom;

import sample.dao.CrudDAO;
import sample.entity.Customer;

import java.util.List;

public interface CustomerDAO extends CrudDAO<Customer,String> {

    String getLastCustomrID() throws Exception;

    List<Customer> searchAll(String value) throws Exception;
}
