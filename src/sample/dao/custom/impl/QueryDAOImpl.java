package sample.dao.custom.impl;


import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import sample.dao.custom.QueryDAO;
import sample.entity.CustomEntity;
import sample.entity.OrderDetail;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {
private Session session;

    @Override
    public void setSession(Session session) {
        this.session=session;
    }

    @Override
    public List<CustomEntity> largeOrderTable() throws Exception {
        NativeQuery nativeQuery = session.createNativeQuery("SELECT O.Date as date,O.Id as orderId,O.Customer_ID as customerId" +
                ",C.name as customerName,OD.Item_ID as itemId,I.description as itemDescription," +
                "OD.unitPrice as unitPrice,OD.quantity as qty,I.quantityOnHand as qtyOnHand FROM MYPOSSYSTEM.`Order` O,MYPOSSYSTEM.OrderDetail OD,MYPOSSYSTEM.Item_Details I,MYPOSSYSTEM.Customer_Detail C WHERE " +
                "O.Id=OD.Order_Id AND OD.Item_ID=I.code AND O.Customer_ID=C.id ORDER BY O.id");



        Query<CustomEntity> query = nativeQuery.setResultTransformer(Transformers.aliasToBean(CustomEntity.class));
        List<CustomEntity> list = query.list();

        return list;

    }

    @Override
    public List<CustomEntity> searchOrderDetails(String value) throws Exception {
        NativeQuery nativeQuery = session.createNativeQuery("SELECT O.Date as date,O.Id as orderId,O.Customer_ID as customerId ,C.name as customerName,OD.Item_ID as itemId,I.description as itemDescription,OD.unitPrice as unitPrice,OD.quantity as qty,I.quantityOnHand as qtyOnHand FROM MYPOSSYSTEM.`Order` O INNER JOIN MYPOSSYSTEM.OrderDetail OD ON O.Id=OD.Order_Id INNER JOIN MYPOSSYSTEM.Item_Details I ON OD.Item_ID=I.code INNER JOIN MYPOSSYSTEM.Customer_Detail C ON O.Customer_ID=C.id WHERE O.Id LIKE ?1 OR C.name LIKE ?2 OR I.description LIKE ?3");
                nativeQuery.setParameter(1,value);
                nativeQuery.setParameter(2,value);
                nativeQuery.setParameter(3,value);
        Query<CustomEntity> query = nativeQuery.setResultTransformer(Transformers.aliasToBean(CustomEntity.class));
        List<CustomEntity> list = query.list();

        return list;
    }

    @Override
    public List<CustomEntity> summeryDetail() throws Exception {
        NativeQuery nativeQuery = session.createNativeQuery("SELECT I.code as itemId,I.description as itemDescription,OD.quantity qty,OD.unitPrice unitPrice,(OD.quantity*OD.unitPrice) as total FROM MYPOSSYSTEM.OrderDetail OD INNER JOIN MYPOSSYSTEM.Item_Details I ON I.code=OD.Item_Id INNER JOIN MYPOSSYSTEM.`Order` O ON O.id=OD.Order_Id WHERE O.date=?1");
        nativeQuery.setParameter(1, LocalDate.now());
        Query<CustomEntity> query = nativeQuery.setResultTransformer(Transformers.aliasToBean(CustomEntity.class));
        List<CustomEntity> list = query.list();
        return list;
    }
}
