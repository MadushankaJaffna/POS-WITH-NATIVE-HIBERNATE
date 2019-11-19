package sample.dao.custom.impl;

import org.hibernate.Session;
import sample.dao.CrudDAOImpl;
import sample.dao.custom.OrderDAO;
import sample.entity.Order;

import java.util.List;

public class OrderDAOImpl extends CrudDAOImpl<Order,String> implements OrderDAO {

    @Override
    public String getLastOrderID() throws Exception {
        return (String) session.createQuery("SELECT O.id FROM sample.entity.Order O ORDER BY O.id desc ").setMaxResults(1).uniqueResult();
    }
}
