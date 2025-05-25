package model;

public class OrderItem {
    private String orderNo;
    private String productName;
    private String quantity;
    private String weight;
    private String city;

    public OrderItem(String orderNo, String productName, String quantity, String weight, String city) {
        this.orderNo = orderNo;
        this.productName = productName;
        this.quantity = quantity;
        this.weight = weight;
        this.city = city;
    }

    // Getters
    public String getOrderNo() {
        return orderNo;
    }

    public String getProductName() {
        return productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getWeight() {
        return weight;
    }

    public String getCity() {
        return city;
    }

    // Setters
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "orderNo='" + orderNo + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity='" + quantity + '\'' +
                ", weight='" + weight + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
} 