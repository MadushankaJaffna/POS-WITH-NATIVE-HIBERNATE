package sample.dao.custom.impl;

import org.hibernate.Session;
import sample.dao.CrudDAOImpl;
import sample.dao.custom.CustomerDAO;
import sample.entity.Customer;

import java.util.List;

public class CustomerDAOImpl extends CrudDAOImpl<Customer,String> implements CustomerDAO {

    @Override
    public String getLastCustomrID() throws Exception {
    // return (String) session.createNativeQuery("SELECT id FROM MYPOSSYSTEM.Customer_Detail ORDER BY id DESC LIMIT 1").uniqueResult();
        return (String) session.createQuery("SELECT C.id FROM sample.entity.Customer C ORDER BY C.id DESC").setMaxResults(1).uniqueResult();
    }

    @Override
    public List<Customer> searchAll(String value) throws Exception {
        return session.createNativeQuery("SELECT * FROM MYPOSSYSTEM.Customer_Detail C WHERE C.id LIKE ?1 OR C.name LIKE ?2 OR C.address LIKE ?3", Customer.class)
                .setParameter(1, value)
                .setParameter(2, value)
                .setParameter(3, value).list();
    }


}
