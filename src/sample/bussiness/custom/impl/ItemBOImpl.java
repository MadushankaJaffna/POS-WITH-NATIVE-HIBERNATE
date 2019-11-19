package sample.bussiness.custom.impl;

import org.hibernate.Session;
import sample.bussiness.custom.ItemBO;
import sample.dao.DAOFactory;
import sample.dao.DAOType;
import sample.dao.custom.ItemDAO;
import sample.db.HibernateUtill;
import sample.dto.CustomerDTO;
import sample.dto.ItemDTO;
import sample.entity.Customer;
import sample.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {

    private ItemDAO itemDAO= DAOFactory.getInstance().getDAO(DAOType.ITEM);

    @Override
    public String getLastItemId() throws Exception {
        Session session = HibernateUtill.getSessionFactory().openSession();
        session.beginTransaction();
        itemDAO.setSession(session);
        String lastItemID = itemDAO.getLastItemID();

        session.getTransaction().commit();
        return lastItemID;
    }

    @Override
    public List<ItemDTO> searchAll(String value) throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();

            List<Item> all = itemDAO.searchAllItems(value);

            session.getTransaction().commit();
            List<ItemDTO> objects = new ArrayList<>();

            for (Item data:all) {
                objects.add(new ItemDTO(data.getCode(),data.getDescription(),data.getQuantityOnHand(),data.getUnitPrice()));
            }
            return objects;
        }
    }

    @Override
    public void updateQty(String id, int qty) throws Exception {
        itemDAO.updateQty(id,qty);
    }

    @Override
    public void descQty(String id, int qty) throws Exception {
        itemDAO.descQty(id,qty);
    }

    @Override
    public void save(ItemDTO DTO) throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();

            itemDAO.save(new Item(DTO.getCode(),DTO.getDescription(),DTO.getQuantityOnHand(),DTO.getUnitPrice()));

            session.getTransaction().commit();
        }
    }

    @Override
    public void update(ItemDTO DTO) throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();

            itemDAO.update(new Item(DTO.getCode(),DTO.getDescription(),DTO.getQuantityOnHand(),DTO.getUnitPrice()));


            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(String s) throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();

            itemDAO.delete(s);

            session.getTransaction().commit();
        }
    }

    @Override
    public List<ItemDTO> findAll() throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();

            List<Item> all = itemDAO.findAll();

            session.getTransaction().commit();
            List<ItemDTO> objects = new ArrayList<>();

            for (Item data:all) {
                objects.add(new ItemDTO(data.getCode(),data.getDescription(),data.getQuantityOnHand(),data.getUnitPrice()));
            }
            return objects;
        }
    }

    @Override
    public ItemDTO find(String id) throws Exception {
        try (Session session = HibernateUtill.getSessionFactory().openSession()) {
            itemDAO.setSession(session);
            session.beginTransaction();

            Item item = itemDAO.find(id);

            session.getTransaction().commit();

            return new ItemDTO(item.getCode(),item.getDescription(),item.getQuantityOnHand(),item.getUnitPrice());
        }
    }
}
