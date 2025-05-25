package service;

import model.Order;
import util.Queue;
import util.Node;
import java.util.List;
import java.util.ArrayList;

public class CargoServiceImpl implements CargoService {
    
    private Queue<Order> cargoQueue;
    
    public CargoServiceImpl() {
        cargoQueue = new Queue<>(100);
    }
    
    @Override
    public void addOrderToCargo(Order order) {
        Node<Order> node = new Node<>(order, order.isPremium());
        cargoQueue.enqueue(node, order.isPremium());
    }
    
    @Override
    public Order deliverOrder() {
        if (cargoQueue.isEmpty()) {
            return null;
        }
        
        Node<Order> node = cargoQueue.dequeue();
        return node.getData();
    }
    
    @Override
    public List<Order> getAllCargoOrders() {
        List<Order> result = new ArrayList<>();
        Queue<Order> tempQueue = new Queue<>(100);
        
        // Get all orders from queue
        while (!cargoQueue.isEmpty()) {
            Node<Order> node = cargoQueue.dequeue();
            result.add(node.getData());
            tempQueue.enqueue(node, node.isPriority());
        }
        
        // Restore original queue
        while (!tempQueue.isEmpty()) {
            Node<Order> node = tempQueue.dequeue();
            cargoQueue.enqueue(node, node.isPriority());
        }
        
        return result;
    }
    
    @Override
    public boolean hasCargoOrders() {
        return !cargoQueue.isEmpty();
    }
} 