package service;

import model.Order;
import java.util.List;

public interface CargoService {
    void addOrderToCargo(Order order);
    Order deliverOrder();
    List<Order> getAllCargoOrders();
    boolean hasCargoOrders();
} 