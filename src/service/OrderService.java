package service;

import model.Order;
import model.Product;
import java.util.List;

public interface OrderService {
    Order createOrder(List<Product> products, boolean isPremium, String city);
    Order shipOrder();
    List<Order> getOrdersByCity(String city);
    List<Order> getPremiumOrders();
    List<Order> getNormalOrders();
    boolean hasOrdersToShip();
} 