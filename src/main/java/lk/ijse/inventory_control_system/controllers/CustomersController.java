package lk.ijse.inventory_control_system.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.SQLException;
import java.util.List;
import lk.ijse.inventory_control_system.dto.CustomerComboDTO;
import lk.ijse.inventory_control_system.dto.CustomersDTO;
import lk.ijse.inventory_control_system.dto.CustomersViewDTO;
import lk.ijse.inventory_control_system.model.CustomersModel;

public class CustomersController {

    @FXML private ComboBox<CustomerComboDTO> customerIDField;
    @FXML private TextField customerNameField;
    @FXML private TextField addressField;
    @FXML private TextField contactNumberField;

    @FXML private TableView<CustomersViewDTO> customersTable;
    @FXML private TableColumn<CustomersViewDTO, Integer> colCustomerID;
    @FXML private TableColumn<CustomersViewDTO, String> colCustomerName;
    @FXML private TableColumn<CustomersViewDTO, String> colAddress;
    @FXML private TableColumn<CustomersViewDTO, String> colContact;

    private final CustomersModel customerModel = new CustomersModel();

    @FXML
    public void initialize() {
        setupTable();
        loadCustomerTable();
        loadCustomerComboBox();
        
        customersTable.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) {
                customerNameField.setText(selected.getCustomerName());
                addressField.setText(selected.getAddress());
                contactNumberField.setText(selected.getContactNumber());
                
                customerIDField.getItems().stream()
                    .filter(customer -> customer.getCustomerID() == selected.getCustomerID())
                    .findFirst()
                    .ifPresent(customer -> customerIDField.getSelectionModel().select(customer));
            }
        });
    }

    private void setupTable() {
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
    }

    private void loadCustomerTable() {
        try {
            ObservableList<CustomersViewDTO> list = FXCollections.observableArrayList(customerModel.getAllCustomersForView());
            customersTable.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot load customers!").show();
        }
    }

    private void loadCustomerComboBox() {
        try {
            List<CustomerComboDTO> list = customerModel.getCustomersForCombo();
            customerIDField.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot load customers!").show();
        }
    }

    @FXML
    private void saveCustomer() {
        if (customerNameField.getText().isEmpty() ||
            addressField.getText().isEmpty() ||
            contactNumberField.getText().isEmpty()) {
            
            new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
            return;
        }

        try {
            CustomersDTO customer = new CustomersDTO(
                customerNameField.getText(),
                addressField.getText(),
                contactNumberField.getText()
            );

            if (customerModel.saveCustomer(customer)) {
                new Alert(Alert.AlertType.INFORMATION, "Customer saved successfully!").show();
                loadCustomerTable();
                loadCustomerComboBox();
                resetFields();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void updateCustomer() {
        if (customerNameField.getText().isEmpty() ||
            addressField.getText().isEmpty() ||
            contactNumberField.getText().isEmpty()) {
            
            new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
            return;
        }

        CustomerComboDTO selected = customerIDField.getValue();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Select a customer to update!").show();
            return;
        }

        try {
            CustomersDTO customer = new CustomersDTO(
                selected.getCustomerID(),
                customerNameField.getText(),
                addressField.getText(),
                contactNumberField.getText()
            );

            if (customerModel.updateCustomer(customer)) {
                new Alert(Alert.AlertType.INFORMATION, "Customer updated successfully!").show();
                loadCustomerTable();
                loadCustomerComboBox();
                resetFields();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error updating customer!").show();
        }
    }

    @FXML
    private void deleteCustomer() {
        CustomerComboDTO selected = customerIDField.getValue();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Select a customer to delete!").show();
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Are you sure you want to delete this customer?");
        confirmAlert.setContentText(
            "Customer: " + selected.getCustomerName() +
            " (ID: " + selected.getCustomerID() + ")"
        );

        var result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (customerModel.deleteCustomer(String.valueOf(selected.getCustomerID()))) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer deleted successfully!").show();
                    loadCustomerTable();
                    loadCustomerComboBox();
                    resetFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete customer!").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error deleting customer!").show();
            }
        }
    }

    @FXML
    private void handleSearchCustomer() {
        CustomerComboDTO selected = customerIDField.getValue();
        if (selected == null) return;

        try {
            CustomersDTO customer = customerModel.searchCustomer(String.valueOf(selected.getCustomerID()));
            if (customer != null) {
                populateFields(customer);
            } else {
                new Alert(Alert.AlertType.ERROR, "Customer not found!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error searching customer!").show();
        }
    }

    @FXML
    private void resetFields() {
        customerIDField.getSelectionModel().clearSelection();
        customerNameField.clear();
        addressField.clear();
        contactNumberField.clear();
    }

    private void populateFields(CustomersDTO customer) {
        customerNameField.setText(customer.getCustomerName());
        addressField.setText(customer.getAddress());
        contactNumberField.setText(customer.getContactNumber());

        customerIDField.getItems().stream()
            .filter(c -> c.getCustomerID() == customer.getCustomerID())
            .findFirst()
            .ifPresent(c -> customerIDField.getSelectionModel().select(c));
    }
}