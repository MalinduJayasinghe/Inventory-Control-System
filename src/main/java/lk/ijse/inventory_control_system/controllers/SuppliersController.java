package lk.ijse.inventory_control_system.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.inventory_control_system.dto.SupplierComboDTO;
import lk.ijse.inventory_control_system.dto.SuppliersDTO;
import lk.ijse.inventory_control_system.dto.SuppliersViewDTO;
import lk.ijse.inventory_control_system.model.SuppliersModel;

import java.sql.SQLException;
import java.util.List;

public class SuppliersController {

    @FXML private ComboBox<SupplierComboDTO> supplierComboBox;
    @FXML private TextField supplierNameField;
    @FXML private TextField addressField;
    @FXML private TextField emailField;
    @FXML private TextField contactNumberField;

    @FXML private TableView<SuppliersViewDTO> suppliersTable;
    @FXML private TableColumn<SuppliersViewDTO, Integer> colSupplierID;
    @FXML private TableColumn<SuppliersViewDTO, String> colSupplierName;
    @FXML private TableColumn<SuppliersViewDTO, String> colAddress;
    @FXML private TableColumn<SuppliersViewDTO, String> colEmail;
    @FXML private TableColumn<SuppliersViewDTO, String> colContact;

    private final SuppliersModel suppliersModel = new SuppliersModel();

    @FXML
    public void initialize() {
        setupTable();
        loadSupplierComboBox();
        loadSupplierTable();
        
        // Add table selection listener to populate fields when clicking on table rows
        suppliersTable.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) {
                supplierNameField.setText(selected.getSupplierName());
                addressField.setText(selected.getAddress());
                emailField.setText(selected.getEmail());
                contactNumberField.setText(selected.getContactNumber());
                
                // Set the supplier combo box
                supplierComboBox.getItems().stream()
                    .filter(supplier -> supplier.getSupplierID() == selected.getSupplierID())
                    .findFirst()
                    .ifPresent(supplier -> supplierComboBox.getSelectionModel().select(supplier));
            }
        });
    }

    private void setupTable() {
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        colSupplierName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
    }

    private void loadSupplierTable() {
        try {
            List<SuppliersViewDTO> list = suppliersModel.getAllSuppliersForView();
            suppliersTable.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot load suppliers!").show();
        }
    }

    private void loadSupplierComboBox() {
        try {
            List<SupplierComboDTO> list = suppliersModel.getSuppliersForCombo();
            supplierComboBox.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot load suppliers!").show();
        }
    }

    @FXML
    private void searchSupplier() {
        SupplierComboDTO selected = supplierComboBox.getValue();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a supplier to search!").show();
            return;
        }

        try {
            SuppliersDTO supplier = suppliersModel.searchSupplier(String.valueOf(selected.getSupplierID()));
            if (supplier != null) {
                populateFields(supplier);
            } else {
                new Alert(Alert.AlertType.ERROR, "Supplier not found!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error searching supplier!").show();
        }
    }

    @FXML
    private void saveSupplier() {
        // Validation
        if (supplierNameField.getText().isEmpty() ||
            addressField.getText().isEmpty() ||
            emailField.getText().isEmpty() ||
            contactNumberField.getText().isEmpty()) {
            
            new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
            return;
        }
        
        // Basic email validation
        if (!emailField.getText().contains("@")) {
            new Alert(Alert.AlertType.WARNING, "Please enter a valid email address!").show();
            return;
        }

        try {
            SuppliersDTO supplier = new SuppliersDTO(
                supplierNameField.getText(),
                addressField.getText(),
                emailField.getText(),
                contactNumberField.getText()
            );

            if (suppliersModel.saveSupplier(supplier)) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier saved successfully!").show();
                loadSupplierTable();
                loadSupplierComboBox();
                resetFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save supplier!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error saving supplier!\n" + e.getMessage()).show();
        }
    }

    @FXML
    private void updateSupplier() {
        // Validation
        SupplierComboDTO selected = supplierComboBox.getValue();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a supplier to update!").show();
            return;
        }
        
        if (supplierNameField.getText().isEmpty() ||
            addressField.getText().isEmpty() ||
            emailField.getText().isEmpty() ||
            contactNumberField.getText().isEmpty()) {
            
            new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
            return;
        }
        
        // Basic email validation
        if (!emailField.getText().contains("@")) {
            new Alert(Alert.AlertType.WARNING, "Please enter a valid email address!").show();
            return;
        }

        try {
            SuppliersDTO supplier = new SuppliersDTO(
                selected.getSupplierID(),
                supplierNameField.getText(),
                addressField.getText(),
                emailField.getText(),
                contactNumberField.getText()
            );

            if (suppliersModel.updateSupplier(supplier)) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier updated successfully!").show();
                loadSupplierTable();
                loadSupplierComboBox();
                resetFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update supplier!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error updating supplier!\n" + e.getMessage()).show();
        }
    }

    @FXML
    private void deleteSupplier() {
        SupplierComboDTO selectedSupplier = supplierComboBox.getValue();
        if (selectedSupplier == null) {
            new Alert(Alert.AlertType.WARNING, "Select a supplier to delete!").show();
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Are you sure you want to delete this supplier?");
        confirmAlert.setContentText(
            "Supplier: " + selectedSupplier.getSupplierName() + 
            " (ID: " + selectedSupplier.getSupplierID() + ")"
        );

        var result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (suppliersModel.deleteSupplier(String.valueOf(selectedSupplier.getSupplierID()))) {
                    new Alert(Alert.AlertType.INFORMATION, "Supplier deleted successfully!").show();
                    loadSupplierTable();
                    loadSupplierComboBox();
                    resetFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete supplier!").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error deleting supplier!\n" + e.getMessage()).show();
            }
        }
    }

    @FXML
    private void resetFields() {
        supplierComboBox.getSelectionModel().clearSelection();
        supplierNameField.clear();
        addressField.clear();
        emailField.clear();
        contactNumberField.clear();
    }

    private void populateFields(SuppliersDTO supplier) {
        supplierNameField.setText(supplier.getSupplierName());
        addressField.setText(supplier.getAddress());
        emailField.setText(supplier.getEmail());
        contactNumberField.setText(supplier.getContactNumber());
        
        // Set the supplier combo box
        supplierComboBox.getItems().stream()
            .filter(s -> s.getSupplierID() == supplier.getSupplierID())
            .findFirst()
            .ifPresent(s -> supplierComboBox.getSelectionModel().select(s));
    }
}