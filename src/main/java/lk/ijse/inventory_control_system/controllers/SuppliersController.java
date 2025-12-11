package lk.ijse.inventory_control_system.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.ijse.inventory_control_system.dto.SuppliersDTO;
import lk.ijse.inventory_control_system.model.SuppliersModel;

import java.sql.SQLException;

public class SuppliersController {

    @FXML private TextField supplierIDField;
    @FXML private TextField supplierNameField;
    @FXML private TextField addressField;
    @FXML private TextField emailField;
    @FXML private TextField contactNumberField;
    
    @FXML private TableView<SuppliersDTO> suppliersTable;
    @FXML private TableColumn<SuppliersDTO, Integer> colSupplierID;
    @FXML private TableColumn<SuppliersDTO, String> colSupplierName;
    @FXML private TableColumn<SuppliersDTO, String> colAddress;
    @FXML private TableColumn<SuppliersDTO, String> colEmail;
    @FXML private TableColumn<SuppliersDTO, String> colContact;

    private final SuppliersModel suppliersModel = new SuppliersModel();

    @FXML
    public void initialize() {
        setupTable();
        loadSupplierTable();
    }

    private void setupTable() {
        colSupplierID.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("supplierID"));
        colSupplierName.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("supplierName"));
        colAddress.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("email"));
        colContact.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("contactNumber"));
    }

    private void loadSupplierTable() {
        try {
            ObservableList<SuppliersDTO> list =
                FXCollections.observableArrayList(suppliersModel.getAllSuppliers());
            suppliersTable.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot load suppliers!").show();
        }
    }

    @FXML
    private void saveSupplier() {
        try {
            SuppliersDTO supplier = new SuppliersDTO(
                    Integer.parseInt(supplierIDField.getText()),
                    supplierNameField.getText(),
                    addressField.getText(),
                    emailField.getText(),
                    contactNumberField.getText()
            );

            if (suppliersModel.saveSupplier(supplier)) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier saved!").show();
                loadSupplierTable();
                resetFields();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error saving supplier!").show();
        }
    }

    @FXML
    private void updateSupplier() {
        try {
            SuppliersDTO supplier = new SuppliersDTO(
                Integer.parseInt(supplierIDField.getText()),
                supplierNameField.getText(),
                addressField.getText(),
                emailField.getText(),
                contactNumberField.getText()
            );

            if (suppliersModel.updateSupplier(supplier)) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier updated!").show();
                loadSupplierTable();
                resetFields();
            }

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error updating supplier!").show();
        }
    }

    @FXML
    private void deleteSupplier() {
        try {
            if (suppliersModel.deleteSupplier(supplierIDField.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier deleted!").show();
                loadSupplierTable();
                resetFields();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error deleting supplier!").show();
        }
    }

    @FXML
    private void searchSupplier() {
        try {
            SuppliersDTO supplier = suppliersModel.searchSupplier(supplierIDField.getText());
            if (supplier != null) {
                supplierNameField.setText(supplier.getSupplierName());
                addressField.setText(supplier.getAddress());
                emailField.setText(supplier.getEmail());
                contactNumberField.setText(supplier.getContactNumber());
            } else {
                new Alert(Alert.AlertType.WARNING, "Supplier not found!").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void resetFields() {
        supplierIDField.clear();
        supplierNameField.clear();
        addressField.clear();
        emailField.clear();
        contactNumberField.clear();
    }
}
