package model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderNo;
    private List<Product> products;
    private boolean isPremium;

    public Order(boolean isPremium) {
        this.products = new ArrayList<>();
        this.isPremium = isPremium;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public double getTotalWeight() {
        double totalWeight = 0;
        for (Product product : products) {
            totalWeight += product.getTotalWeight();
        }
        return totalWeight;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Product product : products) {
            sb.append(product.getName()).append(" (").append(product.getQuantity()).append("), ");
        }
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }
}