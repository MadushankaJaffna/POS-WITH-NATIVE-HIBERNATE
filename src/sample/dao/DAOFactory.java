package sample.dao;

import sample.dao.custom.impl.*;
public class DAOFactory {
    private static DAOFactory daoFactory;

    public DAOFactory() {
    }

    public static DAOFactory getInstance(){
        return (daoFactory==null) ?(daoFactory= new DAOFactory()):daoFactory;
    }

    public<T extends SuperDAO>T getDAO(DAOType daoType){
        switch (daoType){
            case ORDER:return (T) new OrderDAOImpl();
            case ITEM:return (T) new ItemDAOImpl();
            case CUSTOMER:return (T) new CustomerDAOImpl();
            case OrderDetail:return (T) new OrderDetailDAOImpl();
            case QueryDAO:return (T) new QueryDAOImpl();
            default:return null;
        }
    }
}
