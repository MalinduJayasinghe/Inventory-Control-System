package lk.ijse.inventory_control_system.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import lk.ijse.inventory_control_system.App;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DashboardController {
    
    @FXML private Button Dashboard;
    @FXML private Button Items;
    @FXML private Button DamagedItems;
    @FXML private Button PurchaseOrders;
    @FXML private Button CustomerOrders;
    @FXML private Button Customers;
    @FXML private Button Suppliers;
    @FXML private Button logoutButton;
    
    @FXML private Label pageTitle;
    @FXML private Label currentDate;
    @FXML private Label currentUser;
    
    @FXML private Pane contentPane;
    
    private final Map<Button, String> baseStyles = new HashMap<>();
    private final Map<Button, String> hoverStyles = new HashMap<>();
    
    @FXML
    public void initialize() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        currentDate.setText(today.format(formatter));
        
        currentUser.setText("Admin"); 
        
        styleButton(Items, "#1e293b");
        styleButton(DamagedItems, "#1e293b");
        styleButton(PurchaseOrders, "#1e293b");
        styleButton(CustomerOrders, "#1e293b");
        styleButton(Customers, "#1e293b");
        styleButton(Suppliers, "#1e293b");
        styleButton(logoutButton, "#dc2626");
        
        setupNavigation(Dashboard, "DashboardOverview");
        setupNavigation(Items, "Items");
        setupNavigation(DamagedItems, "DamagedItem");
        setupNavigation(PurchaseOrders, "PurchaseOrder");
        setupNavigation(CustomerOrders, "CustomerOrder");
        setupNavigation(Customers, "Customers");
        setupNavigation(Suppliers, "Suppliers");
        
        logoutButton.setOnAction(e -> {
            try {
                App.loadWindow("Login");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        Dashboard.fire();
    }
    
    @FXML
    private void styleButton(Button btn, String baseColor) {
        String normalStyle = "-fx-background-color: transparent;"
            + "-fx-text-fill: #e2e8f0;"
            + "-fx-font-size: 15px;"
            + "-fx-alignment: CENTER-LEFT;"
            + "-fx-padding: 0 15;"
            + "-fx-background-radius: 10;"
            + "-fx-cursor: hand;";

        String hoverStyle = "-fx-background-color: #334155;"
            + "-fx-text-fill: white;"
            + "-fx-font-size: 15px;"
            + "-fx-alignment: CENTER-LEFT;"
            + "-fx-padding: 0 15;"
            + "-fx-background-radius: 10;"
            + "-fx-cursor: hand;";

        if (btn == logoutButton) {
            normalStyle = "-fx-background-color: " + baseColor + ";"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 15px;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 10;"
                + "-fx-cursor: hand;";

            hoverStyle = "-fx-background-color: #b91c1c;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 15px;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 10;"
                + "-fx-cursor: hand;"
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0.3, 0, 2);";
        }

        baseStyles.put(btn, normalStyle);
        hoverStyles.put(btn, hoverStyle);

        btn.setStyle(normalStyle);

        btn.setOnMouseEntered(e -> {
            if (!btn.getStyle().contains("#3b82f6")) { 
                btn.setStyle(hoverStyles.get(btn));
            }
        });
        btn.setOnMouseExited(e -> {
            if (!btn.getStyle().contains("#3b82f6")) { 
                btn.setStyle(baseStyles.get(btn));
            }
        });
    }
    
    @FXML
    private void setupNavigation(Button button, String fxmlName) {
        button.setOnAction(e -> {
            try {
                if (button == Dashboard) {
                    pageTitle.setText("Dashboard Overview");
                } else {
                    pageTitle.setText(button.getText().replace("📦 ", "").replace("⚠️ ", "")
                        .replace("🛒 ", "").replace("🛍️ ", "").replace("👥 ", "").replace("🏢 ", ""));
                }
                
                Pane pane = FXMLLoader.load(getClass().getResource("/lk/ijse/inventory_control_system/" + fxmlName + ".fxml"));
                contentPane.getChildren().clear();
                contentPane.getChildren().add(pane);
                
                pane.getScene().getRoot().setUserData(this);
                
                AnchorPane.setTopAnchor(pane, 0.0);
                AnchorPane.setBottomAnchor(pane, 0.0);
                AnchorPane.setLeftAnchor(pane, 0.0);
                AnchorPane.setRightAnchor(pane, 0.0);
                
                highlightButton(button);
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    
    public void loadView(Pane pane, String title) {
        pageTitle.setText(title);
        contentPane.getChildren().clear();
        contentPane.getChildren().add(pane);
        
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        
        if (title.equals("Items")) {
            highlightButton(Items);
        } else if (title.equals("Purchase Orders")) {
            highlightButton(PurchaseOrders);
        }
    }
    
    private void highlightButton(Button button) {
        for (Button btn : baseStyles.keySet()) {
            if (btn == logoutButton) continue; 
            
            if (btn == button) {
                if (btn == Dashboard) {
                    btn.setStyle("-fx-background-color: #3b82f6; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-background-radius: 12; -fx-cursor: hand;");
                } else {
                    btn.setStyle("-fx-background-color: #334155; -fx-text-fill: white; -fx-font-size: 15px; -fx-alignment: CENTER-LEFT; -fx-padding: 0 15; -fx-background-radius: 10; -fx-cursor: hand;");
                }
            } else {
                btn.setStyle(baseStyles.get(btn));
            }
        }
    }
}