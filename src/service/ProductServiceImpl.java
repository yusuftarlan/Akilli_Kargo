package service;

import model.Product;
import java.util.List;
import java.util.ArrayList;

public class ProductServiceImpl implements ProductService {
    
    private List<Product> products;
    
    public ProductServiceImpl() {
        initializeProducts();
    }
    
    private void initializeProducts() {
        products = new ArrayList<>();
        products.add(new Product("Tablet", 1.0));
        products.add(new Product("Laptop", 2.0));
        products.add(new Product("Monitör", 3.0));
        products.add(new Product("Kasa", 4.0));
        products.add(new Product("Airfryer", 5.0));
        products.add(new Product("Tost Makinesi", 3.0));
        products.add(new Product("Robot Süpürge", 2.0));
    }
    
    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }
    
    @Override
    public List<Product> getSelectedProducts() {
        List<Product> selectedProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getQuantity() > 0) {
                // Create a copy to avoid modifying original
                Product selectedProduct = new Product(product.getName(), product.getWeight());
                selectedProduct.setQuantity(product.getQuantity());
                selectedProducts.add(selectedProduct);
            }
        }
        return selectedProducts;
    }
    
    @Override
    public void updateProductQuantity(int index, int quantity) {
        if (index >= 0 && index < products.size()) {
            products.get(index).setQuantity(quantity);
        }
    }
    
    @Override
    public void resetAllQuantities() {
        for (Product product : products) {
            product.setQuantity(0);
        }
    }
    
    @Override
    public Product getProduct(int index) {
        if (index >= 0 && index < products.size()) {
            return products.get(index);
        }
        return null;
    }
    
    @Override
    public boolean hasSelectedProducts() {
        return products.stream().anyMatch(product -> product.getQuantity() > 0);
    }
    
    @Override
    public double getTotalWeight() {
        return products.stream()
                .mapToDouble(Product::getTotalWeight)
                .sum();
    }
    
    @Override
    public int getProductCount() {
        return products.size();
    }
    
    @Override
    public void clearSelections() {
        resetAllQuantities();
    }
    
    @Override
    public boolean isProductSelected(int index) {
        if (index >= 0 && index < products.size()) {
            return products.get(index).getQuantity() > 0;
        }
        return false;
    }
    
    @Override
    public int getTotalSelectedQuantity() {
        return products.stream()
                .mapToInt(Product::getQuantity)
                .sum();
    }
} 