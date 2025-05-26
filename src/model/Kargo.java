package model;

import java.util.ArrayList;
import java.util.List;

public class Kargo {
    List<Product> products = new ArrayList<Product>() ;


    public void add(List <Product> products) {
        this.products.addAll(products);
    }
    public List<Product> getProducts() {
        return products;
    }

    public double getWeight(){
        double weight = 0;
        for(Product product : products){
            weight += product.getTotalWeight();
        }
        return weight;
    }
}
