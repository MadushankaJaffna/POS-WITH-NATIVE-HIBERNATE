package sample.dao.custom.impl;

import org.hibernate.Session;
import sample.dao.CrudDAOImpl;
import sample.dao.custom.OrderDetailDAO;
import sample.entity.OrderDetail;
import sample.entity.OrderDetailPK;

public class OrderDetailDAOImpl extends CrudDAOImpl<OrderDetail,OrderDetailPK> implements OrderDetailDAO {

}
