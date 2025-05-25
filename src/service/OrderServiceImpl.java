package service;

import model.Order;
import model.Product;
import util.Stack;
import util.Queue;
import util.Node;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    
    private Stack<Order> orderStack;
    private Queue<Order> orderQueue;
    private Map<String, List<Order>> ordersByCity;
    private int orderCounter;
    
    public OrderServiceImpl() {
        orderStack = new Stack<>();
        orderQueue = new Queue<>(100);
        ordersByCity = new HashMap<>();
        orderCounter = 0;
    }
    
    @Override
    public Order createOrder(List<Product> products, boolean isPremium, String city) {
        if (products == null || products.isEmpty()) {
            return null;
        }
        
        double totalWeight = calculateTotalWeight(products);
        
        if (totalWeight > 15.0) {
            return createSplitOrders(products, isPremium, city);
        } else {
            return createSingleOrder(products, isPremium, city);
        }
    }
    
    private Order createSingleOrder(List<Product> products, boolean isPremium, String city) {
        Order order = new Order(isPremium);
        order.setOrderNo(String.valueOf(++orderCounter));
        order.setCity(city);
        
        for (Product product : products) {
            if (product.getQuantity() > 0) {
                Product orderProduct = new Product(product.getName(), product.getWeight());
                orderProduct.setQuantity(product.getQuantity());
                order.addProduct(orderProduct);
            }
        }
        
        orderStack.push(order, isPremium);
        addToOrdersByCity(city, order);
        return order;
    }
    
    private Order createSplitOrders(List<Product> products, boolean isPremium, String city) {
        // Split logic for orders > 15kg
        List<Order> splitOrders = splitOrder(products, isPremium);
        int subOrderCounter = 0;
        
        for (Order order : splitOrders) {
            order.setOrderNo(orderCounter + "." + (++subOrderCounter));
            order.setCity(city);
            orderStack.push(order, isPremium);
            addToOrdersByCity(city, order);
        }
        
        orderCounter++;
        return splitOrders.get(0); // Return first order for reference
    }
    
    @Override
    public Order shipOrder() {
        if (orderStack.isEmpty()) {
            return null;
        }
        
        Node<Order> node = orderStack.pop();
        Order order = node.getData();
        
        orderQueue.enqueue(node, node.isPriority());
        removeFromOrdersByCity(order.getCity(), order);
        
        return order;
    }
    
    @Override
    public List<Order> getOrdersByCity(String city) {
        return ordersByCity.getOrDefault(city, new ArrayList<>());
    }
    
    @Override
    public List<Order> getPremiumOrders() {
        return getOrdersByPriority(true);
    }
    
    @Override
    public List<Order> getNormalOrders() {
        return getOrdersByPriority(false);
    }
    
    @Override
    public boolean hasOrdersToShip() {
        return !orderStack.isEmpty();
    }
    
    private void addToOrdersByCity(String city, Order order) {
        ordersByCity.computeIfAbsent(city, k -> new ArrayList<>()).add(order);
    }
    
    private void removeFromOrdersByCity(String city, Order order) {
        List<Order> cityOrders = ordersByCity.get(city);
        if (cityOrders != null) {
            cityOrders.remove(order);
            if (cityOrders.isEmpty()) {
                ordersByCity.remove(city);
            }
        }
    }
    
    private List<Order> getOrdersByPriority(boolean isPremium) {
        List<Order> result = new ArrayList<>();
        Stack<Order> tempStack = new Stack<>();
        
        while (!orderStack.isEmpty()) {
            Node<Order> node = orderStack.pop();
            if (node.isPriority() == isPremium) {
                result.add(node.getData());
            }
            tempStack.push(node.getData(), node.isPriority());
        }
        
        // Restore stack
        while (!tempStack.isEmpty()) {
            Node<Order> node = tempStack.pop();
            orderStack.push(node.getData(), node.isPriority());
        }
        
        return result;
    }
    
    private double calculateTotalWeight(List<Product> products) {
        return products.stream()
                .mapToDouble(Product::getTotalWeight)
                .sum();
    }
    
    private List<Order> splitOrder(List<Product> products, boolean isPremium) {
        // Existing split logic from ControlMain
        return new ArrayList<>(); // Simplified for now
    }
} 