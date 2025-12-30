package lk.ijse.inventory_control_system.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import lk.ijse.inventory_control_system.App;

public class DashboardOverviewController {
    
    @FXML private Label totalItemsLabel;
    @FXML private Label lowStockLabel;
    @FXML private Label totalOrdersLabel;
    @FXML private Label damagedItemsLabel;
    @FXML private Button addItemButton;
    @FXML private Button createOrderButton;
    
    @FXML
    public void initialize() {
        loadDashboardStatistics();
    }
    
    private void loadDashboardStatistics() {
        totalItemsLabel.setText("0");
        lowStockLabel.setText("0");
        totalOrdersLabel.setText("0");
        damagedItemsLabel.setText("0");
    }
    
    @FXML
    private void quickAddItem() throws IOException{
        System.out.println("Quick Add Item clicked");
    }
    
    @FXML
    private void quickCreateOrder() throws IOException{
        System.out.println("Quick Create Order clicked");
    }
}