package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import model.Product;
import model.OrderItem;
import service.OrderService;
import service.OrderServiceImpl;
import service.ProductService;
import service.ProductServiceImpl;
import util.Stack;
import util.Queue;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class OrderController {
    
    @FXML private RadioButton radioPremium;
    @FXML private RadioButton radioNormal;
    @FXML private ComboBox<String> comboCities;
    @FXML private Button btnOrder;
    @FXML private Button btnShip;
    
    @FXML private TableView<OrderItem> tablePremium;
    @FXML private TableView<OrderItem> tableNormal;
    
    @FXML private TableColumn<OrderItem, String> colPOrderNo;
    @FXML private TableColumn<OrderItem, String> colPProducts;
    @FXML private TableColumn<OrderItem, String> colPQuantities;
    @FXML private TableColumn<OrderItem, String> colPWeight;
    @FXML private TableColumn<OrderItem, String> colPCity;
    
    @FXML private TableColumn<OrderItem, String> colNOrderNo;
    @FXML private TableColumn<OrderItem, String> colNProducts;
    @FXML private TableColumn<OrderItem, String> colNQuantities;
    @FXML private TableColumn<OrderItem, String> colNWeight;
    @FXML private TableColumn<OrderItem, String> colNCity;
    
    private OrderService orderService;
    private ProductService productService;
    private ObservableList<OrderItem> premiumItems;
    private ObservableList<OrderItem> normalItems;
    
    // City-Order mapping for efficiency
    private Map<String, List<Order>> ordersByCity;
    
    public void initialize() {
        orderService = new OrderServiceImpl();
        productService = new ProductServiceImpl();
        premiumItems = FXCollections.observableArrayList();
        normalItems = FXCollections.observableArrayList();
        ordersByCity = new HashMap<>();
        
        initializeCityComboBox();
        setupTableColumns();
    }
    
    private void initializeCityComboBox() {
        String[] turkishCities = {
            "Adana", "Adıyaman", "Afyonkarahisar", "Ağrı", "Aksaray", "Amasya", "Ankara", 
            "Antalya", "Ardahan", "Artvin", "Aydın", "Balıkesir", "Bartın", "Batman", 
            "Bayburt", "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", 
            "Çanakkale", "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Düzce", "Edirne", 
            "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun", 
            "Gümüşhane", "Hakkari", "Hatay", "Iğdır", "Isparta", "İstanbul", "İzmir", 
            "Kahramanmaraş", "Karabük", "Karaman", "Kars", "Kastamonu", "Kayseri", 
            "Kilis", "Kırıkkale", "Kırklareli", "Kırşehir", "Kocaeli", "Konya", 
            "Kütahya", "Malatya", "Manisa", "Mardin", "Mersin", "Muğla", "Muş", 
            "Nevşehir", "Niğde", "Ordu", "Osmaniye", "Rize", "Sakarya", "Samsun", 
            "Şanlıurfa", "Siirt", "Sinop", "Şırnak", "Sivas", "Tekirdağ", "Tokat", 
            "Trabzon", "Tunceli", "Uşak", "Van", "Yalova", "Yozgat", "Zonguldak"
        };
        comboCities.getItems().addAll(turkishCities);
        comboCities.setPromptText("Şehir Seçiniz");
    }
    
    @FXML
    void handleOrder(ActionEvent event) {
        if (!isValidOrder()) return;
        
        boolean isPremium = radioPremium.isSelected();
        String selectedCity = comboCities.getValue();
        
        Order order = orderService.createOrder(getSelectedProducts(), isPremium, selectedCity);
        if (order != null) {
            updateOrderTable();
            resetForm();
            productService.resetAllQuantities();
            showAlert("Sipariş başarıyla oluşturuldu!");
        } else {
            showAlert("Sipariş oluşturulamadı. Lütfen ürün seçiniz.");
        }
    }
    
    @FXML
    void handleShip(ActionEvent event) {
        Order shippedOrder = orderService.shipOrder();
        if (shippedOrder != null) {
            updateOrderTable();
            showAlert("Sipariş " + shippedOrder.getOrderNo() + " kargoya gönderildi!");
        } else {
            showAlert("Gönderilecek sipariş bulunmamaktadır.");
        }
    }
    
    private boolean isValidOrder() {
        if (!radioPremium.isSelected() && !radioNormal.isSelected()) {
            showAlert("Lütfen müşteri tipini seçiniz.");
            return false;
        }
        if (comboCities.getValue() == null) {
            showAlert("Lütfen şehir seçiniz.");
            return false;
        }
        
        List<Product> selectedProducts = productService.getSelectedProducts();
        if (selectedProducts == null || selectedProducts.isEmpty()) {
            showAlert("Lütfen en az bir ürün seçiniz.");
            return false;
        }
        
        return true;
    }
    
    private List<Product> getSelectedProducts() {
        return productService.getSelectedProducts();
    }
    
    private void updateOrderTable() {
        premiumItems.clear();
        normalItems.clear();
        
        List<Order> premiumOrders = orderService.getPremiumOrders();
        List<Order> normalOrders = orderService.getNormalOrders();
        
        for (Order order : premiumOrders) {
            premiumItems.add(createOrderItem(order));
        }
        
        for (Order order : normalOrders) {
            normalItems.add(createOrderItem(order));
        }
    }
    
    private OrderItem createOrderItem(Order order) {
        StringBuilder productNames = new StringBuilder();
        StringBuilder quantities = new StringBuilder();
        
        for (Product product : order.getProducts()) {
            if (productNames.length() > 0) {
                productNames.append(", ");
                quantities.append(", ");
            }
            productNames.append(product.getName());
            quantities.append(product.getQuantity());
        }
        
        return new OrderItem(
            order.getOrderNo(),
            productNames.toString(),
            quantities.toString(),
            String.format("%.1f kg", order.getTotalWeight()),
            order.getCity()
        );
    }
    
    private void resetForm() {
        comboCities.setValue(null);
        radioPremium.setSelected(false);
        radioNormal.setSelected(false);
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bilgi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void setupTableColumns() {
        // Premium table columns
        colPOrderNo.setCellValueFactory(new PropertyValueFactory<>("orderNo"));
        colPProducts.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colPQuantities.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colPCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        tablePremium.setItems(premiumItems);

        // Normal table columns
        colNOrderNo.setCellValueFactory(new PropertyValueFactory<>("orderNo"));
        colNProducts.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colNQuantities.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colNWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colNCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        tableNormal.setItems(normalItems);
    }
    
    public ProductService getProductService() {
        return productService;
    }
} 