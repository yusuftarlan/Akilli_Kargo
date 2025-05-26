package model;

import java.util.ArrayList;
import java.util.List;

public class Kargo {
    List<Product> products = new ArrayList<Product>();

    public void add(List<Product> nOrders) {
        products.addAll(nOrders);
    }
    public double getWeight(){
        double weight = 0;
        for (Product product : products) {
            weight += product.getTotalWeight();
        }
        return weight;
    }
    public List<Product> getProducts() {
        return products;
    }
}
