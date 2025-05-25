package model;

import java.util.ArrayList;
import java.util.List;
import util.Node;
import util.Stack;

public class Order {
    private String orderNo;
    private Stack<Product> products;
    private boolean isPremium;
    private String city;

    public Order(boolean isPremium) {
        this.products = new Stack<>();
        this.isPremium = isPremium;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void addProduct(Product product) {
        products.push(product, false);
    }

    public Product removeProduct() {
        if (!products.isEmpty()) {
            Node<Product> node = products.pop();
            return node.getData();
        }
        return null;
    }

    public List<Product> getProducts() {
        List<Product> productList = new ArrayList<>();
        Stack<Product> tempStack = new Stack<>();
        
        while (!products.isEmpty()) {
            Node<Product> node = products.pop();
            Product product = node.getData();
            productList.add(product);
            tempStack.push(product, false);
        }
        
        while (!tempStack.isEmpty()) {
            Node<Product> node = tempStack.pop();
            products.push(node.getData(), false);
        }
        
        return productList;
    }

    public boolean hasProducts() {
        return !products.isEmpty();
    }

    public int getProductCount() {
        return products.size();
    }

    public boolean isPremium() {
        return isPremium;
    }

    public double getTotalWeight() {
        double totalWeight = 0;
        List<Product> productList = getProducts();
        for (Product product : productList) {
            totalWeight += product.getTotalWeight();
        }
        return totalWeight;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<Product> productList = getProducts();
        for (Product product : productList) {
            sb.append(product.getName()).append(" (").append(product.getQuantity()).append("), ");
        }
        if (sb.length() > 2) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }
}