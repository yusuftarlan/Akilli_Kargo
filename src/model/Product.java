package model;

public class Product {
    private String name;
    private double weight;
    private int quantity;

    public Product(String name, double weight) {
        this.name = name;
        this.weight = weight;
        this.quantity = 0;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public void decrementQuantity() {
        if (this.quantity > 0) {
            this.quantity--;
        }
    }

    public double getTotalWeight() {
        return weight * quantity;
    }
}