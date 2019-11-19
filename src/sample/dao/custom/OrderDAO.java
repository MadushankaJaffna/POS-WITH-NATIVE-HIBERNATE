package sample.dao.custom;

import sample.dao.CrudDAO;
import sample.entity.Order;

public interface OrderDAO extends CrudDAO<Order,String> {

    String getLastOrderID() throws Exception;

}
