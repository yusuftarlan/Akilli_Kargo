package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Order;
import model.OrderItem;
import service.CargoService;
import service.CargoServiceImpl;
import util.Queue;
import util.Node;
import java.util.List;

public class CargoController {
    
    @FXML private TableView<OrderItem> tableShipping;
    @FXML private TableColumn<OrderItem, String> colCargoOrderNo;
    @FXML private TableColumn<OrderItem, String> colCargoProduct;
    @FXML private TableColumn<OrderItem, String> colCargoQuantity;
    @FXML private TableColumn<OrderItem, String> colCargoCity;
    @FXML private Button btnSendCargo;
    
    private CargoService cargoService;
    private ObservableList<OrderItem> cargoItems;
    
    public void initialize() {
        cargoService = new CargoServiceImpl();
        cargoItems = FXCollections.observableArrayList();
        setupTableColumns();
    }
    
    @FXML
    void handleSendCargo(ActionEvent event) {
        Order deliveredOrder = cargoService.deliverOrder();
        if (deliveredOrder != null) {
            updateCargoTable();
            showAlert("Sipariş " + deliveredOrder.getOrderNo() + " başarıyla teslim edildi!");
        } else {
            showAlert("Gönderilecek kargo bulunmamaktadır.");
        }
    }
    
    public void updateCargoTable() {
        cargoItems.clear();
        List<Order> cargoOrders = cargoService.getAllCargoOrders();
        
        for (Order order : cargoOrders) {
            cargoItems.add(createCargoItem(order));
        }
    }
    
    private OrderItem createCargoItem(Order order) {
        StringBuilder productNames = new StringBuilder();
        StringBuilder quantities = new StringBuilder();
        
        for (model.Product product : order.getProducts()) {
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
            "",  // Weight column not needed for cargo
            order.getCity()
        );
    }
    
    private void setupTableColumns() {
        colCargoOrderNo.setCellValueFactory(new PropertyValueFactory<>("orderNo"));
        colCargoProduct.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colCargoQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colCargoCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        tableShipping.setItems(cargoItems);
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bilgi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public void addOrderToCargo(Order order) {
        cargoService.addOrderToCargo(order);
        updateCargoTable();
    }
} 