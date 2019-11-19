package sample.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Customer_Detail")
public class Customer implements SuperEntity {

    @Id
    private String id;
    private String name;
    private String address;

    @OneToMany(mappedBy = "customer",cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.REFRESH})
    private List<Order> order = new ArrayList<>();

    public Customer() {
    }

    public Customer(String id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void addOrder(Order order) {
        order.setCustomer(this);
        this.order.add(order);
    }
    public void removeOrder(Order order){
        if(order.getCustomer()!= this){
            throw new RuntimeException("invalid Customer Order");
        }
        else{
            order.setCustomer(null);
            this.order.remove(order);
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
