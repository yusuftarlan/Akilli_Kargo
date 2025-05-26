package model;

import java.util.ArrayList;
import java.util.List;

public class Kargo {
    List<Order> orders = new ArrayList<Order>();

    public void add(Order order) {

    }
    public double getWeight() {
        orders.get(0).getTotalWeight();
    }
}
