package sample.dao.custom;

import sample.dao.SuperDAO;
import sample.entity.CustomEntity;

import java.util.List;

public interface QueryDAO extends SuperDAO {
    List<CustomEntity> largeOrderTable() throws Exception;
    List<CustomEntity> searchOrderDetails(String value) throws Exception;
    List<CustomEntity> summeryDetail() throws Exception;
}
