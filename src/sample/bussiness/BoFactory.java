package sample.bussiness;

import sample.bussiness.custom.impl.CustomerBOImpl;
import sample.bussiness.custom.impl.ItemBOImpl;
import sample.bussiness.custom.impl.OrderBOImpl;

public class BoFactory {

    private static BoFactory boFactory;

    private BoFactory() {
    }
    public static BoFactory getInstance(){
        return (boFactory==null)?(boFactory=new BoFactory()):boFactory;
    }
    public<T extends SuperBO>T getBO(BOType boType){
        switch (boType){
            case Item:return (T) new ItemBOImpl();
            case Order:return (T) new OrderBOImpl();
            case Customer:return (T) new CustomerBOImpl();
            default:return null;
        }
    }

}
