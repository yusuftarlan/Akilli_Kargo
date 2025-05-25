package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import model.Product;
import service.ProductService;
import service.ProductServiceImpl;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ProductController {
    
    @FXML private TextField txtQuantity1, txtQuantity2, txtQuantity3, txtQuantity4, txtQuantity5, txtQuantity6, txtQuantity7;
    @FXML private Button btnPlus1, btnPlus2, btnPlus3, btnPlus4, btnPlus5, btnPlus6, btnPlus7;
    @FXML private Button btnMinus1, btnMinus2, btnMinus3, btnMinus4, btnMinus5, btnMinus6, btnMinus7;
    @FXML private ImageView imageview1, imageview2, imageview3, imageview4, imageview5, imageview6, imageview7;
    
    private ProductService productService;
    
    // Button-TextField-Product mapping for efficiency
    private Map<Button, TextField> buttonToTextFieldMap;
    private Map<Button, Integer> buttonToProductIndexMap;
    
    public void initialize() {
        productService = new ProductServiceImpl();
        initializeButtonMappings();
        loadProductImages();
    }
    
    private void initializeButtonMappings() {
        buttonToTextFieldMap = new HashMap<>();
        buttonToProductIndexMap = new HashMap<>();
        
        // Plus buttons
        buttonToTextFieldMap.put(btnPlus1, txtQuantity1);
        buttonToTextFieldMap.put(btnPlus2, txtQuantity2);
        buttonToTextFieldMap.put(btnPlus3, txtQuantity3);
        buttonToTextFieldMap.put(btnPlus4, txtQuantity4);
        buttonToTextFieldMap.put(btnPlus5, txtQuantity5);
        buttonToTextFieldMap.put(btnPlus6, txtQuantity6);
        buttonToTextFieldMap.put(btnPlus7, txtQuantity7);
        
        // Minus buttons
        buttonToTextFieldMap.put(btnMinus1, txtQuantity1);
        buttonToTextFieldMap.put(btnMinus2, txtQuantity2);
        buttonToTextFieldMap.put(btnMinus3, txtQuantity3);
        buttonToTextFieldMap.put(btnMinus4, txtQuantity4);
        buttonToTextFieldMap.put(btnMinus5, txtQuantity5);
        buttonToTextFieldMap.put(btnMinus6, txtQuantity6);
        buttonToTextFieldMap.put(btnMinus7, txtQuantity7);
        
        // Product indexes
        buttonToProductIndexMap.put(btnPlus1, 0); buttonToProductIndexMap.put(btnMinus1, 0);
        buttonToProductIndexMap.put(btnPlus2, 1); buttonToProductIndexMap.put(btnMinus2, 1);
        buttonToProductIndexMap.put(btnPlus3, 2); buttonToProductIndexMap.put(btnMinus3, 2);
        buttonToProductIndexMap.put(btnPlus4, 3); buttonToProductIndexMap.put(btnMinus4, 3);
        buttonToProductIndexMap.put(btnPlus5, 4); buttonToProductIndexMap.put(btnMinus5, 4);
        buttonToProductIndexMap.put(btnPlus6, 5); buttonToProductIndexMap.put(btnMinus6, 5);
        buttonToProductIndexMap.put(btnPlus7, 6); buttonToProductIndexMap.put(btnMinus7, 6);
    }
    
    @FXML
    void handleIncrease(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        updateQuantity(sourceButton, 1);
    }
    
    @FXML
    void handleDecrease(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        updateQuantity(sourceButton, -1);
    }
    
    private void updateQuantity(Button button, int change) {
        TextField textField = buttonToTextFieldMap.get(button);
        Integer productIndex = buttonToProductIndexMap.get(button);
        
        if (textField != null && productIndex != null) {
            int currentValue = Integer.parseInt(textField.getText());
            int newValue = Math.max(0, currentValue + change);
            
            textField.setText(String.valueOf(newValue));
            productService.updateProductQuantity(productIndex, newValue);
        }
    }
    
    public List<Product> getSelectedProducts() {
        return productService.getSelectedProducts();
    }
    
    public void resetQuantities() {
        TextField[] textFields = {txtQuantity1, txtQuantity2, txtQuantity3, txtQuantity4, txtQuantity5, txtQuantity6, txtQuantity7};
        for (TextField field : textFields) {
            field.setText("0");
        }
        productService.resetAllQuantities();
    }
    
    private void loadProductImages() {
        // Image loading logic...
    }
} 