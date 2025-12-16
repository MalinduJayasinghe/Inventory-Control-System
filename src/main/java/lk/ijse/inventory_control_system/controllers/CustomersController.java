package lk.ijse.inventory_control_system.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.sql.SQLException;
import lk.ijse.inventory_control_system.dto.CustomersDTO;
import lk.ijse.inventory_control_system.model.CustomersModel;

public class CustomersController {

    @FXML private TextField customerIDField;
    @FXML private TextField customerNameField;
    @FXML private TextField addressField;
    @FXML private TextField contactNumberField;

    @FXML private TableView<CustomersDTO> customersTable;
    @FXML private TableColumn<CustomersDTO, Integer> colCustomerID;
    @FXML private TableColumn<CustomersDTO, String> colCustomerName;
    @FXML private TableColumn<CustomersDTO, String> colAddress;
    @FXML private TableColumn<CustomersDTO, String> colContact;

    private final CustomersModel customerModel = new CustomersModel();

    @FXML
    public void initialize() {
        setupTable();
        loadCustomerTable();
    }

    private void setupTable() {
        colCustomerID.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("customerID"));
        colCustomerName.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("customerName"));
        colAddress.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("contactNumber"));
    }

    private void loadCustomerTable() {
        try {
            ObservableList<CustomersDTO> list = FXCollections.observableArrayList(customerModel.getAllCustomers());
            customersTable.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot load customers!").show();
        }
    }

    @FXML
    private void saveCustomer() {
        try {
            CustomersDTO customer = new CustomersDTO(
                customerNameField.getText(),
                addressField.getText(),
                contactNumberField.getText()
            );

            if (customerModel.saveCustomer(customer)) {
                new Alert(Alert.AlertType.INFORMATION, "Customer saved successfully!").show();
                loadCustomerTable();
                resetFields();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void updateCustomer() {
        try {
            CustomersDTO customer = new CustomersDTO(
                Integer.parseInt(customerIDField.getText()),
                customerNameField.getText(),
                addressField.getText(),
                contactNumberField.getText()
            );

            if (customerModel.updateCustomer(customer)) {
                new Alert(Alert.AlertType.INFORMATION, "Customer updated successfully!").show();
                loadCustomerTable();
                resetFields();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error updating customer!").show();
        }
    }

    @FXML
    private void deleteCustomer() {
        try {
            if (customerModel.deleteCustomer(customerIDField.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "Customer deleted successfully!").show();
                loadCustomerTable();
                resetFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error deleting customer!").show();
        }
    }

    @FXML
    private void handleSearchCustomer(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            try {
                CustomersDTO customer = customerModel.searchCustomer(customerIDField.getText());
                if(customer != null) {
                    populateFields(customer);
                } else {
                    new Alert(Alert.AlertType.ERROR, "Customer not found!").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void resetFields() {
        customerIDField.clear();
        customerNameField.clear();
        addressField.clear();
        contactNumberField.clear();
    }

    private void populateFields(CustomersDTO customer) {
        customerIDField.setText(String.valueOf(customer.getCustomerID()));
        customerNameField.setText(customer.getCustomerName());
        addressField.setText(customer.getAddress());
        contactNumberField.setText(customer.getContactNumber());
    }
}
