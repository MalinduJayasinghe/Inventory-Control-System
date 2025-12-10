package lk.ijse.inventory_control_system.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.inventory_control_system.App;

public class LoginController {

    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML 
    private Button signInButton;
    
    @FXML
    private void login() throws IOException {
        String realUsername = "admin";
        String realPassword = "12345";
        
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        System.out.println(username + " - " + password);
        
        if(username.equals(realUsername) & password.equals(realPassword)) {
        App.loadWindow("Dashboard");
        }
    }
    
    @FXML
    private void styleSignInButton(Button btn) {

        String normalStyle =
            "-fx-background-color: #4e4376;" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 20;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;";

        String hoverStyle =
                normalStyle +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.35), 10, 0.3, 0, 3);";

        btn.setStyle(normalStyle);

        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));

        btn.setOnMouseExited(e -> {
            btn.setStyle(normalStyle);
            btn.setEffect(null);
            }
        );
    }
    
    @FXML
    public void initialize() {
        styleSignInButton(signInButton);
    }
}