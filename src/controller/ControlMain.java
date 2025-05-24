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
    private TableColumn<OrderItem, String> colCargoCity;
    @FXML
    private Button btnSendCargo;

    // Data models
    private List<Product> products;
    private Stack<Order> orderStack;
    private Queue<Order> orderQueue;
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
        orderStack = new Stack<>();
        orderQueue = new Queue<>(100);

        // Initialize observable lists
        premiumItems = FXCollections.observableArrayList();
        normalItems  = FXCollections.observableArrayList();
        cargoItems   = FXCollections.observableArrayList();

        // ComboBox için detaylı test ve konfigürasyon
        System.out.println("ComboBox initializing...");
        
        if (comboCities != null) {
            // Önceki items'ları temizle
            comboCities.getItems().clear();
            
            // İstanbul ve Ankara ekle
            comboCities.getItems().addAll("İstanbul", "Ankara");
            
            // ComboBox ayarları
            comboCities.setFocusTraversable(true);
            comboCities.setEditable(false);
            comboCities.setPromptText("Şehir Seçiniz");
            
            // Default seçim yapma (isteğe bağlı)
            comboCities.setValue(null);
            
            System.out.println("ComboBox items: " + comboCities.getItems());
            System.out.println("ComboBox size: " + comboCities.getItems().size());
            
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
                orderStack.push(order, isPremium);
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
            orderStack.push(order, isPremium);
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
        List<Product> selectedProducts = new ArrayList<>();

        // Get selected products
        for (Product product : products) {
            if (product.getQuantity() > 0) {
                Product orderProduct = new Product(product.getName(), product.getWeight());
                orderProduct.setQuantity(product.getQuantity());
                selectedProducts.add(orderProduct);
            }
        }

        Order currentOrder = new Order(isPremium);
        double currentWeight = 0;

        for (Product product : selectedProducts) {
            // If product itself exceeds 15kg, need to split it into multiple orders
            if (product.getWeight() > 15.0) {
                int remainingQuantity = product.getQuantity();
                while (remainingQuantity > 0) {
                    Order singleProductOrder = new Order(isPremium);
                    Product singleProduct = new Product(product.getName(), product.getWeight());
                    singleProduct.setQuantity(1);
                    singleProductOrder.addProduct(singleProduct);
                    orders.add(singleProductOrder);
                    remainingQuantity--;
                }
            } else {
                // Check if adding this product would exceed weight limit
                double productWeight = product.getTotalWeight();
                int remainingQuantity = product.getQuantity();

                while (remainingQuantity > 0) {
                    // Calculate how many items of this product can fit
                    double availableWeight = 15.0 - currentWeight;
                    int itemsToAdd = Math.min(remainingQuantity, (int) (availableWeight / product.getWeight()));

                    if (itemsToAdd > 0) {
                        // Add items to current order
                        Product partialProduct = new Product(product.getName(), product.getWeight());
                        partialProduct.setQuantity(itemsToAdd);
                        currentOrder.addProduct(partialProduct);

                        currentWeight += partialProduct.getTotalWeight();
                        remainingQuantity -= itemsToAdd;
                    }

                    // If we can't add more items or we've added all items
                    if (itemsToAdd == 0 || remainingQuantity == 0) {
                        if (currentOrder.getProducts().size() > 0) {
                            orders.add(currentOrder);
                            currentOrder = new Order(isPremium);
                            currentWeight = 0;
                        }
                    }
                }
            }
        }

        // Add final order if it has products
        if (currentOrder.getProducts().size() > 0) {
            orders.add(currentOrder);
        }

        return orders;
    }

    private void updateOrderTable() {
        premiumItems.clear();
        normalItems.clear();

        Stack<Order> tempStack = new Stack<>();

        while (!orderStack.isEmpty()) {
            Node<Order> node = orderStack.pop();
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

            tempStack.push(order, isPremium);
        }

        // Restore stack
        while (!tempStack.isEmpty()) {
            Node<Order> node = tempStack.pop();
            orderStack.push(node.getData(), node.isPriority());
        }
    }

    @FXML
    void handleShip(ActionEvent event) {
        if (orderStack.isEmpty()) {
            showAlert("Gönderilecek sipariş bulunmamaktadır.");
            return;
        }

        // Get the top order from the stack
        Node<Order> node = orderStack.pop();
        Order topOrder = node.getData();
        boolean isPremium = node.isPriority();

        // Add the order to the cargo queue
        if (orderQueue == null) {
            orderQueue = new Queue<>(100);
        }
        orderQueue.enqueue(new Node<>(topOrder), isPremium);

        // Update both order tables
        updateOrderTable();

        // Update cargo table
        updateCargoTable();

        showAlert("Sipariş " + topOrder.getOrderNo() + " kargoya gönderildi!");
    }

    private void updateCargoTable() {
        cargoItems.clear();

        // Create a temporary queue to preserve the original
        Queue<Order> tempQueue = new Queue<>(100);

        while (!orderQueue.isEmpty()) {
            Node<Order> node = orderQueue.dequeue();
            Order order = node.getData();

            // Group products for each order
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

            // Add a row for the entire order
            cargoItems.add(new OrderItem(
                    order.getOrderNo(),
                    productNames.toString(),
                    quantities.toString(),
                    "",
                    order.getCity()
            ));

            tempQueue.enqueue(node, node.isPriority());
        }

        // Restore queue
        while (!tempQueue.isEmpty()) {
            Node<Order> node = tempQueue.dequeue();
            orderQueue.enqueue(node, node.isPriority());
        }
    }

    @FXML
    void handleSendCargo(ActionEvent event) {
        if (orderQueue.isEmpty()) {
            showAlert("Gönderilecek kargo bulunmamaktadır.");
            return;
        }

        // Remove first order from queue (just one order at a time)
        Node<Order> node = orderQueue.dequeue();

        // Update cargo table
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
