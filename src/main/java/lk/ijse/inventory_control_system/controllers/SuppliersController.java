package lk.ijse.inventory_control_system.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
            List<SuppliersViewDTO> list = suppliersModel.getAllSuppliersForView();
            suppliersTable.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Cannot load suppliers!").show();
        }
    }

    private void loadSupplierComboBox() {
        try {
            List<SupplierComboDTO> list = suppliersModel.getSuppliersForCombo();
            supplierComboBox.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Cannot load suppliers!").show();
        }
    }

    @FXML
    private void searchSupplier() {
        SupplierComboDTO selected = supplierComboBox.getValue();
        if (selected == null) return;

        try {
            SuppliersDTO supplier = suppliersModel.searchSupplier(String.valueOf(selected.getSupplierID()));
            if (supplier != null) populateFields(supplier);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Cannot search supplier!").show();
        }
    }

    @FXML
    private void saveSupplier() {
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
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error saving supplier!").show();
        }
    }

    @FXML
    private void updateSupplier() {
        SupplierComboDTO selected = supplierComboBox.getValue();
        if (selected == null) return;

        try {
            SuppliersDTO supplier = new SuppliersDTO(
                selected.getSupplierID(),
                supplierNameField.getText(),
                addressField.getText(),
                emailField.getText(),
                contactNumberField.getText()
            );

            if (suppliersModel.updateSupplier(supplier)) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier updated!").show();
                loadSupplierTable();
                loadSupplierComboBox();
                resetFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error updating supplier!").show();
        }
    }

    @FXML
    private void deleteSupplier() {
        try {
            SupplierComboDTO selectedSupplier = supplierComboBox.getValue();
            if (selectedSupplier == null) {
                new Alert(Alert.AlertType.WARNING, "Select a supplier to delete!").show();
                return;
            }

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Delete");
            confirmAlert.setHeaderText("Are you sure you want to delete this supplier?");
            confirmAlert.setContentText("Supplier: " + selectedSupplier.getSupplierName() + " (ID: " + selectedSupplier.getSupplierID() + ")");

            var result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (suppliersModel.deleteSupplier(String.valueOf(selectedSupplier.getSupplierID()))) {
                    new Alert(Alert.AlertType.INFORMATION, "Supplier deleted!").show();
                    loadSupplierTable();
                    resetFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete supplier!").show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error deleting supplier!").show();
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
    }
}
