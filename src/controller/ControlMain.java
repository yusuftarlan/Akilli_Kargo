package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Order;
import model.Product;
import util.Node;
import util.Queue;
import util.Stack;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControlMain implements Initializable {

    // Market Page
    @FXML
    private Button btnOrder;
    @FXML
    private ToggleGroup customerType;
    @FXML
    private RadioButton radioPremium;
    @FXML
    private RadioButton radioNormal;
    @FXML
    private ComboBox<String> comboCities;

    // Product Quantities
    @FXML
    private TextField txtQuantity1;
    @FXML
    private TextField txtQuantity2;
    @FXML
    private TextField txtQuantity3;
    @FXML
    private TextField txtQuantity4;
    @FXML
    private TextField txtQuantity5;
    @FXML
    private TextField txtQuantity6;
    @FXML
    private TextField txtQuantity7;

    // Product +/- Buttons
    @FXML
    private Button btnPlus1, btnPlus2, btnPlus3, btnPlus4, btnPlus5, btnPlus6, btnPlus7;
    @FXML
    private Button btnMinus1, btnMinus2, btnMinus3, btnMinus4, btnMinus5, btnMinus6, btnMinus7;

    @FXML
    private ImageView imageview1,imageview2,imageview3,imageview4,imageview5,imageview6,imageview7;

    @FXML
    private TableView<OrderItem> tablePremium;
    @FXML
    private TableColumn<OrderItem, String> colPOrderNo;
    @FXML
    private TableColumn<OrderItem, String> colPProducts;
    @FXML
    private TableColumn<OrderItem, String> colPQuantities;
    @FXML
    private TableColumn<OrderItem, String> colPWeight;
    @FXML
    private TableColumn<OrderItem, String> colPCity;

    @FXML
    private TableView<OrderItem> tableNormal;
    @FXML
    private TableColumn<OrderItem, String> colNOrderNo;
    @FXML
    private TableColumn<OrderItem, String> colNProducts;
    @FXML
    private TableColumn<OrderItem, String> colNQuantities;
    @FXML
    private TableColumn<OrderItem, String> colNWeight;
    @FXML
    private TableColumn<OrderItem, String> colNCity;
    @FXML
    private Button btnShip;

    // Cargo Page
    @FXML
    private TableView<OrderItem> tableShipping;
    @FXML
    private TableColumn<OrderItem, String> colCargoOrderNo;
    @FXML
    private TableColumn<OrderItem, String> colCargoProduct;
    @FXML
    private TableColumn<OrderItem, String> colCargoQuantity;
    @FXML
    private TableColumn<OrderItem, String> colCargoWeight;
    @FXML
    private TableColumn<OrderItem, String> colCargoCity;
    @FXML
    private Button btnSendCargo;

    // Data models
    private List<Product> products;
    private Queue<Order> sellerOrderQueue;
    private Queue<Order> cargoQueue;
    private ObservableList<OrderItem> premiumItems;
    private ObservableList<OrderItem> normalItems;
    private ObservableList<OrderItem> cargoItems;

    // Order counter
    private int orderCounter = 0;

    @FXML
    private Button btnTestCombo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize products
        products = new ArrayList<>();
        products.add(new Product("Ürün 1", 1.0));
        products.add(new Product("Ürün 2", 2.0));
        products.add(new Product("Ürün 3", 3.0));
        products.add(new Product("Ürün 4", 4.0));
        products.add(new Product("Ürün 5", 5.0));
        products.add(new Product("Ürün 6", 3.0));
        products.add(new Product("Ürün 7", 2.0));

        // Initialize data structures
        sellerOrderQueue = new Queue<>(100);
        cargoQueue = new Queue<>(100);

        // Initialize observable lists
        premiumItems = FXCollections.observableArrayList();
        normalItems  = FXCollections.observableArrayList();
        cargoItems   = FXCollections.observableArrayList();

        // ComboBox için 81 il listesi
        System.out.println("ComboBox initializing...");
        
        if (comboCities != null) {
            // Önceki items'ları temizle
            comboCities.getItems().clear();
            
            // Türkiye'nin 81 ili (alfabetik sıra)
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
            
            // Tüm illeri ComboBox'a ekle
            comboCities.getItems().addAll(turkishCities);
            
            // ComboBox ayarları
            comboCities.setFocusTraversable(true);
            comboCities.setEditable(false);
            comboCities.setPromptText("Şehir Seçiniz");
            
            // Default seçim yapma (isteğe bağlı)
            comboCities.setValue(null);
            
            System.out.println("ComboBox items: " + comboCities.getItems().size() + " il eklendi");
            
            // Event listeners
            comboCities.setOnMouseClicked(event -> {
                System.out.println("ComboBox mouse clicked!");
            });
            
            comboCities.setOnShowing(event -> {
                System.out.println("ComboBox dropdown showing!");
            });
            
            comboCities.setOnAction(event -> {
                System.out.println("Selected city: " + comboCities.getValue());
            });
        } else {
            System.out.println("ERROR: comboCities is null!");
        }

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

        // Cargo table columns
        colCargoOrderNo.setCellValueFactory(new PropertyValueFactory<>("orderNo"));
        colCargoProduct.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colCargoQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colCargoWeight.setCellValueFactory(new PropertyValueFactory<>("weight"));
        colCargoCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        tableShipping.setItems(cargoItems);

        Image image = new Image(getClass().getResource("/images/tablet.jpg").toExternalForm());
        imageview1.setImage(image);

        Image image2 = new Image(getClass().getResource("/images/laptop.jpg").toExternalForm());
        imageview2.setImage(image2);

        Image image3 = new Image(getClass().getResource("/images/monitör.jpg").toExternalForm());
        imageview3.setImage(image3);


        Image image4 = new Image(getClass().getResource("/images/kasa.jpg").toExternalForm());
        imageview4.setImage(image4);

        Image image5 = new Image(getClass().getResource("/images/airfryer.jpg").toExternalForm());
        imageview5.setImage(image5);


        Image image6 = new Image(getClass().getResource("/images/tost makinesi.jpg").toExternalForm());
        imageview6.setImage(image6);

        Image image7 = new Image(getClass().getResource("/images/robotsüpürge.jpg").toExternalForm());
        imageview7.setImage(image7);
    }

    @FXML
    void handleIncrease(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        TextField targetField = null;
        int productIndex = -1;

        if (sourceButton == btnPlus1) { targetField = txtQuantity1; productIndex = 0; }
        else if (sourceButton == btnPlus2) { targetField = txtQuantity2; productIndex = 1; }
        else if (sourceButton == btnPlus3) { targetField = txtQuantity3; productIndex = 2; }
        else if (sourceButton == btnPlus4) { targetField = txtQuantity4; productIndex = 3; }
        else if (sourceButton == btnPlus5) { targetField = txtQuantity5; productIndex = 4; }
        else if (sourceButton == btnPlus6) { targetField = txtQuantity6; productIndex = 5; }
        else if (sourceButton == btnPlus7) { targetField = txtQuantity7; productIndex = 6; }

        if (targetField != null && productIndex >= 0) {
            int currentValue = Integer.parseInt(targetField.getText());
            targetField.setText(String.valueOf(currentValue + 1));
            products.get(productIndex).setQuantity(currentValue + 1);
        }
    }

    @FXML
    void handleDecrease(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        TextField targetField = null;
        int productIndex = -1;

        if (sourceButton == btnMinus1) { targetField = txtQuantity1; productIndex = 0; }
        else if (sourceButton == btnMinus2) { targetField = txtQuantity2; productIndex = 1; }
        else if (sourceButton == btnMinus3) { targetField = txtQuantity3; productIndex = 2; }
        else if (sourceButton == btnMinus4) { targetField = txtQuantity4; productIndex = 3; }
        else if (sourceButton == btnMinus5) { targetField = txtQuantity5; productIndex = 4; }
        else if (sourceButton == btnMinus6) { targetField = txtQuantity6; productIndex = 5; }
        else if (sourceButton == btnMinus7) { targetField = txtQuantity7; productIndex = 6; }

        if (targetField != null && productIndex >= 0) {
            int currentValue = Integer.parseInt(targetField.getText());
            if (currentValue > 0) {
                targetField.setText(String.valueOf(currentValue - 1));
                products.get(productIndex).setQuantity(currentValue - 1);
            }
        }
    }

    @FXML
    void handleOrder(ActionEvent event) {
        // Check if customer type is selected
        if (!radioPremium.isSelected() && !radioNormal.isSelected()) {
            showAlert("Lütfen müşteri tipini seçiniz.");
            return;
        }

        // Check if city is selected
        if (comboCities.getValue() == null || comboCities.getValue().isEmpty()) {
            showAlert("Lütfen şehir seçiniz.");
            return;
        }

        // Check if any product is selected
        boolean hasProduct = false;
        for (Product product : products) {
            if (product.getQuantity() > 0) {
                hasProduct = true;
                break;
            }
        }

        if (!hasProduct) {
            showAlert("Lütfen en az bir ürün seçiniz.");
            return;
        }

        // Calculate total order weight
        double totalWeight = 0;
        for (Product product : products) {
            totalWeight += product.getTotalWeight();
        }

        boolean isPremium = radioPremium.isSelected();
        String selectedCity = comboCities.getValue();
        orderCounter++;

        // If total weight exceeds 15kg, split orders
        if (totalWeight > 15.0) {
            List<Order> orders = splitOrder(isPremium);
            int subOrderCounter = 0;
            for (Order order : orders) {
                order.setOrderNo(orderCounter + "." + (++subOrderCounter));
                order.setCity(selectedCity);
                sellerOrderQueue.enqueue(new Node<>(order), isPremium);
            }
        } else {
            // Create a single order
            Order order = new Order(isPremium);
            order.setOrderNo(String.valueOf(orderCounter));
            order.setCity(selectedCity);
            for (Product product : products) {
                if (product.getQuantity() > 0) {
                    Product orderProduct = new Product(product.getName(), product.getWeight());
                    orderProduct.setQuantity(product.getQuantity());
                    order.addProduct(orderProduct);
                }
            }
            sellerOrderQueue.enqueue(new Node<>(order), isPremium);
        }

        // Update both order tables
        updateOrderTable();

        // Reset product quantities and city selection
        resetQuantities();
        comboCities.setValue(null);

        showAlert("Sipariş başarıyla oluşturuldu!");
    }

    private List<Order> splitOrder(boolean isPremium) {
        List<Order> orders = new ArrayList<>();
        
        // Create a list of individual product items (each with quantity 1)
        List<Product> individualItems = new ArrayList<>();
        
        // Get selected products and create individual items
        for (Product product : products) {
            if (product.getQuantity() > 0) {
                for (int i = 0; i < product.getQuantity(); i++) {
                    Product individualItem = new Product(product.getName(), product.getWeight());
                    individualItem.setQuantity(1);
                    individualItems.add(individualItem);
                }
            }
        }

        // If any single product exceeds 15kg, handle it separately
        List<Product> heavyItems = new ArrayList<>();
        List<Product> normalItems = new ArrayList<>();
        
        for (Product item : individualItems) {
            if (item.getWeight() > 15.0) {
                heavyItems.add(item);
            } else {
                normalItems.add(item);
            }
        }

        // Create orders for heavy items (each gets its own order)
        for (Product heavyItem : heavyItems) {
            Order heavyOrder = new Order(isPremium);
            heavyOrder.addProduct(heavyItem);
            orders.add(heavyOrder);
        }

        // Use bin packing algorithm for normal items
        orders.addAll(optimizedBinPacking(normalItems, isPremium));

        return orders;
    }

    /**
     * Optimized bin packing algorithm to group products efficiently
     * Uses Best Fit Decreasing approach for better space utilization
     */
    private List<Order> optimizedBinPacking(List<Product> items, boolean isPremium) {
        List<Order> orders = new ArrayList<>();
        
        if (items.isEmpty()) {
            return orders;
        }

        // Sort items by weight in descending order (heaviest first)
        items.sort((a, b) -> Double.compare(b.getWeight(), a.getWeight()));

        List<OrderBin> bins = new ArrayList<>();

        for (Product item : items) {
            // Find the best bin that can fit this item
            OrderBin bestBin = null;
            double minRemainingSpace = Double.MAX_VALUE;

            for (OrderBin bin : bins) {
                double remainingSpace = 15.0 - bin.currentWeight;
                if (remainingSpace >= item.getWeight() && remainingSpace < minRemainingSpace) {
                    bestBin = bin;
                    minRemainingSpace = remainingSpace;
                }
            }

            if (bestBin != null) {
                // Add item to the best fitting bin
                bestBin.addProduct(item);
            } else {
                // Create a new bin for this item
                OrderBin newBin = new OrderBin(isPremium);
                newBin.addProduct(item);
                bins.add(newBin);
            }
        }

        // Convert bins to orders
        for (OrderBin bin : bins) {
            orders.add(bin.toOrder());
        }

        return orders;
    }

    /**
     * Helper class for bin packing algorithm
     */
    private static class OrderBin {
        private List<Product> products;
        private double currentWeight;
        private boolean isPremium;

        public OrderBin(boolean isPremium) {
            this.products = new ArrayList<>();
            this.currentWeight = 0.0;
            this.isPremium = isPremium;
        }

        public void addProduct(Product product) {
            // Check if product with same name already exists in this bin
            boolean merged = false;
            for (Product existingProduct : products) {
                if (existingProduct.getName().equals(product.getName()) && 
                    existingProduct.getWeight() == product.getWeight()) {
                    // Merge quantities
                    existingProduct.setQuantity(existingProduct.getQuantity() + product.getQuantity());
                    merged = true;
                    break;
                }
            }
            
            if (!merged) {
                products.add(product);
            }
            
            currentWeight += product.getTotalWeight();
        }

        public Order toOrder() {
            Order order = new Order(isPremium);
            for (Product product : products) {
                order.addProduct(product);
            }
            return order;
        }
    }

    private void updateOrderTable() {
        premiumItems.clear();
        normalItems.clear();

        Queue<Order> tempQueue = new Queue<>(100);

        while (!sellerOrderQueue.isEmpty()) {
            Node<Order> node = sellerOrderQueue.dequeue();
            Order order = node.getData();
            boolean isPremium = node.isPriority();

            // Get all product names from order
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

            ObservableList<OrderItem> targetList = isPremium ? premiumItems : normalItems;
            targetList.add(new OrderItem(
                    order.getOrderNo(),
                    productNames.toString(),
                    quantities.toString(),
                    String.format("%.1f kg", order.getTotalWeight()),
                    order.getCity()
            ));

            tempQueue.enqueue(node, isPremium);
        }

        // Restore queue
        while (!tempQueue.isEmpty()) {
            Node<Order> node = tempQueue.dequeue();
            sellerOrderQueue.enqueue(node, node.isPriority());
        }
    }

    @FXML
    void handleShip(ActionEvent event) {
        if (sellerOrderQueue.isEmpty()) {
            showAlert("Gönderilecek sipariş bulunmamaktadır.");
            return;
        }

        Node<Order> orderToShip = null;
        
        if (sellerOrderQueue.getNumOfPriority() > 0) {
            orderToShip = sellerOrderQueue.dequeue();
        } else {
            orderToShip = sellerOrderQueue.dequeue();
        }

        if (orderToShip != null) {
            Order order = orderToShip.getData();
            boolean isPremium = orderToShip.isPriority();

            cargoQueue.enqueue(new Node<>(order), isPremium);

            updateOrderTable();

            updateCargoTable();

            String orderType = isPremium ? "Premium" : "Normal";
            showAlert(orderType + " sipariş " + order.getOrderNo() + " kargoya gönderildi!");
        }
    }

    private void updateCargoTable() {
        cargoItems.clear();

        Queue<Order> tempQueue = new Queue<>(100);

        while (!cargoQueue.isEmpty()) {
            Node<Order> node = cargoQueue.dequeue();
            Order order = node.getData();

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

            cargoItems.add(new OrderItem(
                    order.getOrderNo(),
                    productNames.toString(),
                    quantities.toString(),
                    String.format("%.1f kg", order.getTotalWeight()),
                    order.getCity()
            ));

            tempQueue.enqueue(node, node.isPriority());
        }

        while (!tempQueue.isEmpty()) {
            Node<Order> node = tempQueue.dequeue();
            cargoQueue.enqueue(node, node.isPriority());
        }
    }

    @FXML
    void handleSendCargo(ActionEvent event) {
        if (cargoQueue.isEmpty()) {
            showAlert("Gönderilecek kargo bulunmamaktadır.");
            return;
        }

        Node<Order> node = cargoQueue.dequeue();

        updateCargoTable();

        showAlert("Sipariş " + node.getData().getOrderNo() + " başarıyla teslim edildi!");
    }

    private void resetQuantities() {
        txtQuantity1.setText("0");
        txtQuantity2.setText("0");
        txtQuantity3.setText("0");
        txtQuantity4.setText("0");
        txtQuantity5.setText("0");
        txtQuantity6.setText("0");
        txtQuantity7.setText("0");

        for (Product product : products) {
            product.setQuantity(0);
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bilgi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void testComboAction(ActionEvent event) {
        System.out.println("=== COMBO TEST ===");
        
        if (comboCities != null) {
            System.out.println("Items: " + comboCities.getItems());
            System.out.println("Current value: " + comboCities.getValue());
            System.out.println("Prompt text: " + comboCities.getPromptText());
            System.out.println("Is editable: " + comboCities.isEditable());
            
            // Programmatically show dropdown
            comboCities.show();
            
            // Set a test value
            comboCities.setValue("İstanbul");
            System.out.println("After setting İstanbul: " + comboCities.getValue());
        } else {
            System.out.println("ComboBox is null!");
        }
        
        System.out.println("==================");
    }

    // Inner class for table items
    public static class OrderItem {
        private String orderNo;
        private String productName;
        private String quantity;
        private String weight;
        private String city;

        public OrderItem(String orderNo, String productName, String quantity, String weight, String city) {
            this.orderNo = orderNo;
            this.productName = productName;
            this.quantity = quantity;
            this.weight = weight;
            this.city = city;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public String getProductName() {
            return productName;
        }

        public String getQuantity() {
            return quantity;
        }

        public String getWeight() {
            return weight;
        }

        public String getCity() {
            return city;
        }
    }
}
