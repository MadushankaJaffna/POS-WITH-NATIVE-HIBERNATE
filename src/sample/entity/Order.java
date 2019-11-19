package sample.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "`Order`")
public class Order implements SuperEntity {
    @Id
    private String id;
    private String date;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.DETACH,CascadeType.MERGE})
    @JoinColumn(name ="Customer_ID",referencedColumnName = "id",nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "order",cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE})
    private List<OrderDetail> orderDetail = new ArrayList<>();

    public Order() {
    }

    public Order(String id, String date, Customer customer) {
        this.id = id;
        this.date = date;
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Customer getCustomer() { return customer; }

    public void setCustomer(Customer customer) { this.customer = customer; }

    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void addOrderDetail(OrderDetail orderDetail) {
        this.orderDetail.add(orderDetail);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
