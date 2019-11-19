package sample.bussiness.custom.impl;

import org.hibernate.Session;
import sample.bussiness.custom.OrderBO;
import sample.dao.DAOFactory;
import sample.dao.DAOType;
import sample.dao.custom.OrderDAO;
import sample.dao.custom.OrderDetailDAO;
import sample.dao.custom.QueryDAO;
import sample.db.HibernateUtill;
import sample.dto.CustomDTO1;
import sample.dto.CustomerDTO;
import sample.dto.OrderDTO;
import sample.dto.OrderDetailsDTO;
import sample.entity.CustomEntity;
import sample.entity.Customer;
import sample.entity.Order;
import sample.entity.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {

    OrderDAO orderDAO = DAOFactory.getInstance().getDAO(DAOType.ORDER);
    OrderDetailDAO orderDetailDAO = DAOFactory.getInstance().getDAO(DAOType.OrderDetail);
    QueryDAO queryDAO = DAOFactory.getInstance().getDAO(DAOType.QueryDAO);

    @Override
    public String getLastOrderId() throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            orderDAO.setSession(session);
            session.beginTransaction();

            String lastOrderID = orderDAO.getLastOrderID();

            session.getTransaction().commit();
            return lastOrderID;
        }
    }

    @Override
    public void save(OrderDTO DTO) throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            orderDAO.setSession(session);
            session.beginTransaction();

            orderDAO.save(new Order(DTO.getId(), DTO.getDate(), new Customer(DTO.getCustomer().getId(), DTO.getCustomer().getName(), DTO.getCustomer().getAddress())));

            session.getTransaction().commit();
        }
    }

    @Override
    public void update(OrderDTO DTO) throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            orderDAO.setSession(session);
            session.beginTransaction();

            orderDAO.update(new Order(DTO.getId(), DTO.getDate(), new Customer(DTO.getCustomer().getId(), DTO.getCustomer().getName(), DTO.getCustomer().getAddress())));


            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(String s) throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            orderDAO.setSession(session);
            session.beginTransaction();

            orderDAO.delete(s);

            session.getTransaction().commit();
        }
    }

    @Override
    public List<OrderDTO> findAll() throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            orderDAO.setSession(session);
            session.beginTransaction();

            List<Order> all = orderDAO.findAll();

            session.getTransaction().commit();
            List<OrderDTO> objects = new ArrayList<>();

            for (Order data : all) {
                objects.add(new OrderDTO(data.getId(), data.getDate(), new CustomerDTO(data.getCustomer().getId(), data.getCustomer().getName(), data.getCustomer().getAddress())));
            }
            return objects;
        }
    }

    @Override
    public OrderDTO find(String s) throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            orderDAO.setSession(session);
            session.beginTransaction();

            Order order = orderDAO.find(s);

            session.getTransaction().commit();

            return new OrderDTO(order.getId(), order.getDate(), new CustomerDTO(order.getCustomer().getId(), order.getCustomer().getName(), order.getCustomer().getAddress()));
        }
    }

    @Override
    public void saveOrderDetail(OrderDetailsDTO orderDetail) throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            orderDetailDAO.setSession(session);
            session.beginTransaction();

            orderDetailDAO.save(new OrderDetail(orderDetail.getOrderId(), orderDetail.getItemId()
                    , orderDetail.getQuantity()
                    , orderDetail.getUnitPrice()));

            session.getTransaction().commit();
        }
    }

    @Override
    public List<CustomDTO1> getAllOrderDetails() throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            queryDAO.setSession(session);
            session.beginTransaction();

            List<CustomEntity> customEntities = queryDAO.largeOrderTable();

            List<CustomDTO1> customDTO1s = new ArrayList<>();
            for (CustomEntity dtos : customEntities) {

                customDTO1s.add(new CustomDTO1(
                        dtos.getDate(),
                        dtos.getOrderId(),
                        dtos.getCustomerId(),
                        dtos.getCustomerName(),
                        dtos.getItemId(),
                        dtos.getItemDescription(),
                        dtos.getUnitPrice(),
                        dtos.getQty(),
                        dtos.getQtyOnHand()
                ));
            }

            session.getTransaction().commit();
            return customDTO1s;
        }
    }

    @Override
    public List<CustomDTO1> searchOrderDetails(String value) throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            queryDAO.setSession(session);
            session.beginTransaction();

            List<CustomEntity> customEntities = queryDAO.searchOrderDetails(value);
            List<CustomDTO1> customDTO1s = new ArrayList<>();
            for (CustomEntity dtos : customEntities) {
                customDTO1s.add(new CustomDTO1(
                        dtos.getDate(),
                        dtos.getOrderId(),
                        dtos.getCustomerId(),
                        dtos.getCustomerName(),
                        dtos.getItemId(),
                        dtos.getItemDescription(),
                        dtos.getUnitPrice(),
                        dtos.getQty(),
                        dtos.getQtyOnHand()
                ));
            }
            session.getTransaction().commit();
            return customDTO1s;
        }
    }

    @Override
    public List<CustomDTO1> summaryDetails() throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            queryDAO.setSession(session);
            session.beginTransaction();

            List<CustomEntity> customEntities = queryDAO.summeryDetail();
            List<CustomDTO1> customDTO1s = new ArrayList<>();
            for (CustomEntity dtos : customEntities) {
                customDTO1s.add(new CustomDTO1(
                        dtos.getItemId(),
                        dtos.getItemDescription(),
                        dtos.getUnitPrice(),
                        dtos.getQty(),
                        dtos.getTotal()
                ));
            }


            session.getTransaction().commit();
            return customDTO1s;
        }
    }
}
