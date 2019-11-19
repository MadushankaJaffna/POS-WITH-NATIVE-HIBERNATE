package sample.bussiness.custom;

import sample.bussiness.CrudBO;
import sample.bussiness.SuperBO;
import sample.dto.CustomDTO1;
import sample.dto.OrderDTO;
import sample.dto.OrderDetailsDTO;
import sample.entity.OrderDetail;

import java.util.List;

public interface OrderBO extends CrudBO<OrderDTO,String> {
    String getLastOrderId() throws Exception;
    void saveOrderDetail(OrderDetailsDTO orderDetail) throws Exception;
    List<CustomDTO1> getAllOrderDetails() throws  Exception;
    List<CustomDTO1> searchOrderDetails(String value) throws  Exception;
    List<CustomDTO1> summaryDetails() throws Exception;

}
