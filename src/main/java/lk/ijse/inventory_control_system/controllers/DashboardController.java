package lk.ijse.inventory_control_system.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lk.ijse.inventory_control_system.App;

public class DashboardController {

    @FXML private Button Items;
    @FXML private Button DamagedItems;
    @FXML private Button PurchaseOrders;
    @FXML private Button CustomerOrders;
    @FXML private Button Customers;
    @FXML private Button Suppliers;
    @FXML private Button logoutButton;
    
    @FXML private Pane contentPane;
    
    private final Map<Button, String> baseStyles = new HashMap<>();
    private final Map<Button, String> hoverStyles = new HashMap<>();

    @FXML
    public void initialize() {

        styleButton(Items, "#2C3E50");
        styleButton(DamagedItems, "#2C3E50");
        styleButton(PurchaseOrders, "#2C3E50");
        styleButton(CustomerOrders, "#2C3E50");
        styleButton(Customers, "#2C3E50");
        styleButton(Suppliers, "#2C3E50");

        styleButton(logoutButton, "#E74C3C");

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
    }

    @FXML
    private void styleButton(Button btn, String baseColor) {

        String normalStyle = "-fx-background-color: " + baseColor + ";"
            + "-fx-background-radius: 8;"
            + "-fx-text-fill: white;"
            + "-fx-font-family: 'Verdana';"
            + "-fx-font-size: 16px;"
            + "-fx-font-weight: bold;";

        String hoverStyle = normalStyle
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 10, 0.3, 0, 3);";

        baseStyles.put(btn, normalStyle);
        hoverStyles.put(btn, hoverStyle);

        btn.setStyle(normalStyle);

        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
        btn.setOnMouseExited(e -> btn.setStyle(normalStyle));
    }

    @FXML
    private void setupNavigation(Button button, String fxmlName) {
        button.setOnAction(e -> {
        try {
            Pane pane = FXMLLoader.load(getClass().getResource("/lk/ijse/inventory_control_system/" + fxmlName + ".fxml"));
            contentPane.getChildren().clear();
            contentPane.getChildren().add(pane);
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

    private void highlightButton(Button button) {
        
        for (Button btn : baseStyles.keySet()) {
            if (btn == button) {
                btn.setStyle(baseStyles.get(btn).replaceFirst("-fx-background-color: #[0-9A-Fa-f]{6};", "-fx-background-color: #1ABC9C;"));
            } else {
                btn.setStyle(baseStyles.get(btn));
            }
        }
    }
}

