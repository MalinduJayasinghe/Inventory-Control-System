package lk.ijse.inventory_control_system.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import java.io.IOException;
import javafx.animation.PauseTransition;
import javafx.scene.effect.DropShadow;
import javafx.util.Duration;
import lk.ijse.inventory_control_system.App;

public class DashboardController {

    @FXML private Button Inventory;
    @FXML private Button Sales;
    @FXML private Button Services;
    @FXML private Button Expenses;
    @FXML private Button Management;
    @FXML private Button logoutButton;

    @FXML private ImageView inventoryIcon;
    @FXML private ImageView salesIcon;
    @FXML private ImageView servicesIcon;
    @FXML private ImageView expensesIcon;
    @FXML private ImageView managementIcon;

    @FXML private Label inventoryLabel;
    @FXML private Label salesLabel;
    @FXML private Label servicesLabel;
    @FXML private Label expensesLabel;
    @FXML private Label managementLabel;
    
    @FXML
    public void initialize(){
        addHoverEffect(inventoryIcon);
        addHoverEffect(salesIcon);
        addHoverEffect(servicesIcon);
        addHoverEffect(expensesIcon);
        addHoverEffect(managementIcon);
        styleButton(Inventory, "#2C3E50");
        styleButton(Sales, "#2C3E50");
        styleButton(Services, "#2C3E50");
        styleButton(Expenses, "#2C3E50");
        styleButton(Management, "#2C3E50");
        styleButton(logoutButton, "#E74C3C");
        setupNavigation(Inventory, inventoryIcon, inventoryLabel, "Inventory");
        setupNavigation(Sales, salesIcon, salesLabel, "Sales");
        setupNavigation(Services, servicesIcon, servicesLabel, "Services");
        setupNavigation(Expenses, expensesIcon, expensesLabel, "Expenses");
        setupNavigation(Management, managementIcon, managementLabel, "Management");

        logoutButton.setOnAction(e -> {
            try {
                App.loadWindow("Login");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    
    @FXML
    private void addHoverEffect(ImageView img) {
        DropShadow shadow = new DropShadow();
        shadow.setRadius(15);

        img.setOnMouseEntered(e -> img.setEffect(shadow));
        img.setOnMouseExited(e -> img.setEffect(null));
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

        btn.setStyle(normalStyle);

        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));

        btn.setOnMouseExited(e -> {
            btn.setStyle(normalStyle);
            btn.setEffect(null);
            }
        );
    }
    
    @FXML
    private void setupNavigation(Button button, ImageView icon, Label label, String fxmlName) {
        
        button.setOnAction(e -> {
            try {
                App.loadWindow(fxmlName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        icon.setOnMouseClicked(e -> {
            try {
                App.loadWindow(fxmlName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        label.setOnMouseClicked(e -> {
            try {
                App.loadWindow(fxmlName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
