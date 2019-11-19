package sample.dao.custom;

import sample.dao.CrudDAO;
import sample.entity.Item;

import java.util.List;

public interface ItemDAO extends CrudDAO<Item,String> {

    String getLastItemID() throws Exception;
    List<Item> searchAllItems(String value) throws  Exception;
    void updateQty(String id,int qty) throws Exception;
    void descQty(String id,int qty) throws Exception;



}
