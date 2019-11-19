package sample.bussiness.custom;

import sample.bussiness.CrudBO;
import sample.bussiness.SuperBO;
import sample.dto.CustomerDTO;

import java.util.List;

public interface CustomerBO extends CrudBO<CustomerDTO,String> {
    String getLastCustomerId() throws Exception;
    List<CustomerDTO> searchAll(String value) throws Exception;
}
