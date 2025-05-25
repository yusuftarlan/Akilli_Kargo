package service;

import model.Product;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    List<Product> getSelectedProducts();
    void updateProductQuantity(int index, int quantity);
    void resetAllQuantities();
    Product getProduct(int index);
    boolean hasSelectedProducts();
    double getTotalWeight();
    int getProductCount();
    
    // Ek metodlar veri yapıları için
    void clearSelections();
    boolean isProductSelected(int index);
    int getTotalSelectedQuantity();
} 