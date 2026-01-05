package lk.ijse.inventory_control_system.controllers;

import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.inventory_control_system.dto.DashboardDTO;
import lk.ijse.inventory_control_system.dto.LowStockItemDTO;
import lk.ijse.inventory_control_system.model.DashboardModel;

public class DashboardOverviewController {
    
    @FXML private Label totalItemsLabel;
    @FXML private Label lowStockLabel;
    @FXML private Label totalOrdersLabel;
    @FXML private Label damagedItemsLabel;
    @FXML private Button addItemButton;
    @FXML private Button createOrderButton;
    @FXML private TableView<LowStockItemDTO> lowStockTable;
    @FXML private TableColumn<LowStockItemDTO, String> colItemName;
    @FXML private TableColumn<LowStockItemDTO, Integer> colQuantity;
    @FXML private TableColumn<LowStockItemDTO, String> colSupplier;
    
    private final DashboardModel dashboardModel = new DashboardModel();
    
    @FXML
    public void initialize() {
        setupLowStockTable();
        loadDashboardStatistics();
        loadLowStockItems();
    }
    
    private void setupLowStockTable() {
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
        colSupplier.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
    }
    
    private void loadDashboardStatistics() {
        try {
            DashboardDTO stats = dashboardModel.getDashboardStatistics();
            
            totalItemsLabel.setText(String.valueOf(stats.getTotalItems()));
            lowStockLabel.setText(String.valueOf(stats.getLowStockItems()));
            totalOrdersLabel.setText(String.valueOf(stats.getTotalOrders()));
            damagedItemsLabel.setText(String.valueOf(stats.getDamagedItems()));
            
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load dashboard statistics!").show();
            
            totalItemsLabel.setText("0");
            lowStockLabel.setText("0");
            totalOrdersLabel.setText("0");
            damagedItemsLabel.setText("0");
        }
    }
    
    private void loadLowStockItems() {
        try {
            lowStockTable.setItems(
                FXCollections.observableArrayList(dashboardModel.getLowStockItems())
            );
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load low stock items!").show();
        }
    }
    
    private DashboardController dashboardController;
    
    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }
    
    @FXML
    private void quickAddItem() {
        try {
            if (dashboardController != null) {
                Pane itemsPane = FXMLLoader.load(
                    getClass().getResource("/lk/ijse/inventory_control_system/Items.fxml")
                );
                
                dashboardController.loadView(itemsPane, "Items");
            } else {
                new Alert(Alert.AlertType.WARNING, "Dashboard controller not initialized!").show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Items view!").show();
        }
    }
    
    @FXML
    private void quickCreateOrder() {
        try {
            if (dashboardController != null) {
                Pane purchaseOrderPane = FXMLLoader.load(
                    getClass().getResource("/lk/ijse/inventory_control_system/PurchaseOrder.fxml")
                );
                
                dashboardController.loadView(purchaseOrderPane, "Purchase Orders");
            } else {
                new Alert(Alert.AlertType.WARNING, "Dashboard controller not initialized!").show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load Purchase Order view!").show();
        }
    }
}