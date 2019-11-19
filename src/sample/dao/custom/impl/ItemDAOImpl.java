package sample.dao.custom.impl;

import org.hibernate.Session;
import sample.dao.CrudDAOImpl;
import sample.dao.custom.ItemDAO;
import sample.entity.Customer;
import sample.entity.Item;

import java.util.List;

public class ItemDAOImpl extends CrudDAOImpl<Item,String> implements ItemDAO {


    @Override
    public String getLastItemID() throws Exception {
        return (String) session.createQuery("SELECT I.id FROM sample.entity.Item I ORDER BY I.id DESC ").setMaxResults(1).uniqueResult();
    }

    @Override
    public List<Item> searchAllItems(String value) throws Exception {
        return session.createNativeQuery("SELECT * FROM MYPOSSYSTEM.Item_Details I WHERE I.code LIKE ?1 OR I.unitPrice LIKE ?2 OR I.description LIKE ?3 OR I.quantityOnHand LIKE ?4", Item.class)
                .setParameter(1, value)
                .setParameter(2, value)
                .setParameter(3, value)
                .setParameter(4, value).list();
    }

    @Override
    public void updateQty(String id, int qty) throws Exception {
        session.createNativeQuery("UPDATE item SET QtyOnHand=(QtyOnHand-?1) WHERE Code=?2")
                .setParameter(1,qty)
                .setParameter(2,id);
    }

    @Override
    public void descQty(String id, int qty) throws Exception {
        session.createNativeQuery("UPDATE item SET QtyOnHand=(QtyOnHand+?1) WHERE Code=?2")
                .setParameter(1,qty)
                .setParameter(2,id);
    }

}
