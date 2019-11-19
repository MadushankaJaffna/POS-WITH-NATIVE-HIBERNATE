package sample.bussiness.custom.impl;

import org.hibernate.Session;
import sample.bussiness.BOType;
import sample.bussiness.BoFactory;
import sample.bussiness.custom.CustomerBO;
import sample.dao.DAOFactory;
import sample.dao.DAOType;
import sample.dao.custom.CustomerDAO;
import sample.db.HibernateUtill;
import sample.dto.CustomerDTO;
import sample.entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    private CustomerDAO customerDao = DAOFactory.getInstance().getDAO(DAOType.CUSTOMER);

    @Override
    public String getLastCustomerId() throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            customerDao.setSession(session);
            session.beginTransaction();

            String lastCustomrID = customerDao.getLastCustomrID();

            session.getTransaction().commit();
        return lastCustomrID;
        }
    }

    @Override
    public List<CustomerDTO> searchAll(String value) throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            customerDao.setSession(session);
            session.getTransaction().begin();

            List<Customer> customers = customerDao.searchAll(value);
            List<CustomerDTO> customerDTO = new ArrayList<>();
            for (Customer data: customers) {
                customerDTO.add(new CustomerDTO(data.getId(),data.getName(),data.getAddress()));
            }
            session.getTransaction().commit();
            return customerDTO;
        }
    }

    @Override
    public void save(CustomerDTO DTO) throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            customerDao.setSession(session);
            session.getTransaction().begin();

            customerDao.save(new Customer(DTO.getId(),DTO.getName(),DTO.getAddress()));

            session.getTransaction().commit();
        }
    }

    @Override
    public void update(CustomerDTO DTO) throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            customerDao.setSession(session);
            session.beginTransaction();

            customerDao.update(new Customer(DTO.getId(),DTO.getName(),DTO.getAddress()));


            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(String s) throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            customerDao.setSession(session);
            session.beginTransaction();

            customerDao.delete(s);

            session.getTransaction().commit();
        }
    }

    @Override
    public List<CustomerDTO> findAll() throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            customerDao.setSession(session);
            session.beginTransaction();
            List<Customer> alCustomers = customerDao.findAll();

            List<CustomerDTO> dtos = new ArrayList<>();
            for (Customer customer : alCustomers) {
                dtos.add(new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress()));
            }
            session.getTransaction().commit();
            return dtos;
        }
    }

    @Override
    public CustomerDTO find(String id) throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            customerDao.setSession(session);
            session.beginTransaction();

            Customer customer = customerDao.find(id);

            session.getTransaction().commit();

            return new CustomerDTO(customer.getId(),customer.getName(),customer.getAddress());
        }
    }
}
