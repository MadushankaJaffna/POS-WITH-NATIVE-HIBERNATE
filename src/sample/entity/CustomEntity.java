package sample.entity;

public class CustomEntity implements SuperEntity {
    private String date;
    private String orderId;
    private String customerId;
    private String customerName;
    private String itemId;
    private String itemDescription;
    private Double unitPrice;
    private int qty;
    private int qtyOnHand;
    private Double total;

    public CustomEntity() {
    }

    public CustomEntity(String date, String orderId, String customerId, String customerName, String itemId, String itemDescription, Double unitPrice, int qty, int qtyOnHand) {
        this.date = date;
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.itemId = itemId;
        this.itemDescription = itemDescription;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.qtyOnHand = qtyOnHand;
    }

    public CustomEntity(String itemId, String itemDescription, Double unitPrice, int qty, Double total) {
        this.itemId = itemId;
        this.itemDescription = itemDescription;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CustomEntity{" +
                "date=" + date +
                ", orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", itemId='" + itemId + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", qtyOnHand=" + qtyOnHand +
                '}';
    }
}
