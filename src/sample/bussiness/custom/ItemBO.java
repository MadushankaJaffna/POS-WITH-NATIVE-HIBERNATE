package sample.bussiness.custom;

import sample.bussiness.CrudBO;
import sample.bussiness.SuperBO;
import sample.dto.CustomerDTO;
import sample.dto.ItemDTO;

import java.util.List;

public interface ItemBO extends CrudBO<ItemDTO,String> {
    String getLastItemId() throws Exception;
    List<ItemDTO> searchAll(String value) throws Exception;
    void updateQty(String id,int qty) throws Exception;
    void descQty(String id,int qty) throws Exception;
}
