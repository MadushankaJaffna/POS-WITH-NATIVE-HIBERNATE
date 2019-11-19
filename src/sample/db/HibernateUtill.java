package sample.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import sample.entity.Customer;
import sample.entity.Item;
import sample.entity.Order;
import sample.entity.OrderDetail;

import java.io.*;
import java.util.Properties;

public class HibernateUtill {
    private static SessionFactory sessionFactory= bulidSessionFactory();


    private static SessionFactory bulidSessionFactory(){

        //File file = new File("../resource/application.properties");
        File file = new File("/home/madushanka/Desktop/POSSQL/resource/application.properties");
        Properties properties = new Properties();
        try {
            try(FileInputStream fis = new FileInputStream(file)) {
                properties.load(fis);
            }
        } catch (Exception e) {
           // e.printStackTrace();
            System.out.println("noo file***************************");

        }


        StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                .loadProperties(file)
                .build();

        Metadata metadata = new MetadataSources( standardRegistry )
                .addAnnotatedClass(Customer.class)
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(OrderDetail.class)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy( ImplicitNamingStrategyJpaCompliantImpl.INSTANCE )
                .build();

        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder()
                .build();

        return sessionFactory;

    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

}
