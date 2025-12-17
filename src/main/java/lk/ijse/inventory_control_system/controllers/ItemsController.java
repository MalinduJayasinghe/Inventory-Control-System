package lk.ijse.inventory_control_system.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.ijse.inventory_control_system.dto.ItemComboDTO;
import lk.ijse.inventory_control_system.dto.ItemsDTO;
import lk.ijse.inventory_control_system.dto.ItemsViewDTO;
import lk.ijse.inventory_control_system.dto.SupplierComboDTO;
import lk.ijse.inventory_control_system.model.ItemsModel;
import lk.ijse.inventory_control_system.model.SuppliersModel;

import java.sql.SQLException;
import java.util.List;

public class ItemsController {

    @FXML private ComboBox<ItemComboDTO> itemComboBox;
    @FXML private ComboBox<SupplierComboDTO> supplierComboBox;

    @FXML private TextField itemNameField;
    @FXML private TextField itemQuantityField;
    @FXML private TextField unitPriceField;
    @FXML private TextField categoryField;

    @FXML private TableView<ItemsViewDTO> itemsTable;
    @FXML private TableColumn<ItemsViewDTO, Integer> colItemID;
    @FXML private TableColumn<ItemsViewDTO, String> colItemName;
    @FXML private TableColumn<ItemsViewDTO, Integer> colQuantity;
    @FXML private TableColumn<ItemsViewDTO, Double> colUnitPrice;
    @FXML private TableColumn<ItemsViewDTO, String> colCategory;
    @FXML private TableColumn<ItemsViewDTO, Integer> colSupplierID;

    private final ItemsModel itemsModel = new ItemsModel();
    private final SuppliersModel suppliersModel = new SuppliersModel();

    @FXML
    public void initialize() {
        setupTable();
        loadItemComboBox();
        loadSupplierComboBox();
        loadItemTable();
        
        itemsTable.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) {
                itemNameField.setText(selected.getItemName());
                itemQuantityField.setText(String.valueOf(selected.getItemQuantity()));
                unitPriceField.setText(String.valueOf(selected.getUnitPrice()));
                categoryField.setText(selected.getCategory());
            }
        });
    }

    private void setupTable() {
        colItemID.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("itemID"));
        colItemName.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("itemQuantity"));
        colUnitPrice.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("unitPrice"));
        colCategory.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("category"));
        colSupplierID.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("supplierID"));
    }

    private void loadItemTable() {
        try {
            List<ItemsViewDTO> list = itemsModel.getAllItemsForView();
            itemsTable.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Cannot load items!").show();
        }
    }

    private void loadItemComboBox() {
        try {
            List<ItemComboDTO> list = itemsModel.getItemsForCombo();
            itemComboBox.setItems(FXCollections.observableArrayList(list));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Cannot load items!").show();
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
    private void searchItem() {
        ItemComboDTO selected = itemComboBox.getValue();
        if (selected == null) return;

        try {
            ItemsDTO item = itemsModel.searchItem(selected.getItemID());
            if (item != null) {
                populateFields(item);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Cannot search item!").show();
        }
    }

    @FXML
    private void saveItem() {
        
        if (itemNameField.getText().isEmpty() ||
            itemQuantityField.getText().isEmpty() ||
            unitPriceField.getText().isEmpty() ||
            categoryField.getText().isEmpty()) {

            new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
            return;
        }
        
        SupplierComboDTO supplier = supplierComboBox.getValue();
        if (supplier == null) return;

        try {
            ItemsDTO item = new ItemsDTO(
                itemNameField.getText(),
                Integer.parseInt(itemQuantityField.getText()),
                Double.parseDouble(unitPriceField.getText()),
                categoryField.getText(),
                supplier.getSupplierID()
            );
            
            int qty;
            double price;

            try {
                qty = Integer.parseInt(itemQuantityField.getText());
                price = Double.parseDouble(unitPriceField.getText());
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.WARNING, "Quantity and Price must be numbers!").show();
                return;
            }

            if (itemsModel.saveItem(item)) {
                new Alert(Alert.AlertType.INFORMATION, "Item saved successfully!").show();
                loadItemTable();
                loadItemComboBox();
                resetFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error saving item!").show();
        }
    }

    @FXML
    private void updateItem() {
        
        if (itemNameField.getText().isEmpty() ||
            itemQuantityField.getText().isEmpty() ||
            unitPriceField.getText().isEmpty() ||
            categoryField.getText().isEmpty()) {

            new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
            return;
        }
        
        ItemComboDTO selected = itemComboBox.getValue();
        SupplierComboDTO supplier = supplierComboBox.getValue();
        if (selected == null || supplier == null) return;

        try {
            ItemsDTO item = new ItemsDTO(
                selected.getItemID(),
                itemNameField.getText(),
                Integer.parseInt(itemQuantityField.getText()),
                Double.parseDouble(unitPriceField.getText()),
                categoryField.getText(),
                supplier.getSupplierID()
            );

            int qty;
            double price;

            try {
                qty = Integer.parseInt(itemQuantityField.getText());
                price = Double.parseDouble(unitPriceField.getText());
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.WARNING, "Quantity and Price must be numbers!").show();
                return;
            }
            
            if (itemsModel.updateItem(item)) {
                new Alert(Alert.AlertType.INFORMATION, "Item updated!").show();
                loadItemTable();
                loadItemComboBox();
                resetFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error updating item!").show();
        }
    }

    @FXML
    private void deleteItem() throws SQLException{
        ItemComboDTO selectedItem = itemComboBox.getValue();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "Select an item to delete!").show();
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Are you sure you want to delete this item?");
        confirmAlert.setContentText(
            "Item: " + selectedItem.getItemName() +
            " (ID: " + selectedItem.getItemID() + ")"
        );

        var result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (itemsModel.deleteItem(selectedItem.getItemID())) {
                new Alert(Alert.AlertType.INFORMATION, "Item deleted!").show();
                loadItemTable();
                resetFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete item!").show();
            }
        }
    }

    @FXML
    private void resetFields() {
        itemComboBox.getSelectionModel().clearSelection();
        supplierComboBox.getSelectionModel().clearSelection();

        itemNameField.clear();
        itemQuantityField.clear();
        unitPriceField.clear();
        categoryField.clear();
    }

    private void populateFields(ItemsDTO item) {
        itemNameField.setText(item.getItemName());
        itemQuantityField.setText(String.valueOf(item.getItemQuantity()));
        unitPriceField.setText(String.valueOf(item.getUnitPrice()));
        categoryField.setText(item.getCategory());

        supplierComboBox.getItems().stream()
            .filter(s -> s.getSupplierID() == item.getSupplierID())
            .findFirst()
            .ifPresent(s -> supplierComboBox.getSelectionModel().select(s));
    }
}
