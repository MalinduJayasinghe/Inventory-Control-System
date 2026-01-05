package lk.ijse.inventory_control_system.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lk.ijse.inventory_control_system.App;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;   
    @FXML private Button signInButton;   
    
    private static final String REAL_USERNAME = "admin";
    private static final String REAL_PASSWORD = "12345";
    private int loginAttempts = 0;
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    
    @FXML
    private void login() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Login Failed", "Please enter both username and password!");
            return;
        }
        
        if (username.equals(REAL_USERNAME) && password.equals(REAL_PASSWORD)) {
            try {
                showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome, " + username + "!");
                App.loadWindow("Dashboard");
                loginAttempts = 0; 
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to load dashboard!");
            }
        } else {
            loginAttempts++;
            int remainingAttempts = MAX_LOGIN_ATTEMPTS - loginAttempts;
            
            if (loginAttempts >= MAX_LOGIN_ATTEMPTS) {
                showAlert(
                    Alert.AlertType.ERROR, 
                    "Login Failed", 
                    "Maximum login attempts exceeded!\nApplication will close."
                );
                System.exit(0);
            } else {
                showAlert(
                    Alert.AlertType.ERROR, 
                    "Login Failed", 
                    "Invalid username or password!\nRemaining attempts: " + remainingAttempts
                );
                passwordField.clear();
                usernameField.requestFocus();
            }
        }
    }
    
    @FXML
    private void handleEnterKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            login();
        }
    }
    
    @FXML
    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        loginAttempts = 0;
        usernameField.requestFocus();
    }
    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
        });
    }
    
    @FXML
    public void initialize() {
        styleSignInButton(signInButton);
        
        usernameField.requestFocus();
        
        usernameField.setOnKeyPressed(this::handleEnterKeyPress);
        passwordField.setOnKeyPressed(this::handleEnterKeyPress);
    }
}