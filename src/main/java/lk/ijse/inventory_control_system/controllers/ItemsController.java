package lk.ijse.inventory_control_system.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.cell.PropertyValueFactory;

import lk.ijse.inventory_control_system.dto.ItemsDTO;
import lk.ijse.inventory_control_system.model.ItemsModel;

public class ItemsController implements Initializable {

    @FXML private TextField itemIdField;
    @FXML private TextField itemNameField;
    @FXML private TextField itemQuantityField;
    @FXML private TextField unitPriceField;
    @FXML private TextField categoryField;
    @FXML private TextField supplierIdField;

    @FXML private TableView<ItemsDTO> tableItems;
    @FXML private TableColumn<ItemsDTO, Integer> colItemID;
    @FXML private TableColumn<ItemsDTO, String> colItemName;
    @FXML private TableColumn<ItemsDTO, Integer> colItemQuantity;
    @FXML private TableColumn<ItemsDTO, Double> colUnitPrice;
    @FXML private TableColumn<ItemsDTO, String> colCategory;
    @FXML private TableColumn<ItemsDTO, Integer> colSupplierID;
    
    @FXML private Button btnSave;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    @FXML private Button btnReset;

    private final ItemsModel itemsModel = new ItemsModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colItemID.setCellValueFactory(new PropertyValueFactory<>("itemID"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colItemQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierID"));

        loadItemTable();

        tableItems.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null) populateFields(newSelection);
        });
    }

    private void populateFields(ItemsDTO item) {
        itemIdField.setText(String.valueOf(item.getItemID()));
        itemNameField.setText(item.getItemName());
        itemQuantityField.setText(String.valueOf(item.getItemQuantity()));
        unitPriceField.setText(String.valueOf(item.getUnitPrice()));
        categoryField.setText(item.getCategory());
        supplierIdField.setText(String.valueOf(item.getSupplierID()));
    }

    @FXML
    private void saveItem() {
        try {
            ItemsDTO item = new ItemsDTO(
            itemNameField.getText(),
            Integer.parseInt(itemQuantityField.getText()),
            Double.parseDouble(unitPriceField.getText()),
            categoryField.getText(),
            Integer.parseInt(supplierIdField.getText())
        );

            if(itemsModel.saveItem(item)) {
                new Alert(Alert.AlertType.INFORMATION, "Item saved successfully!").show();
                loadItemTable();
                resetFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
           new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    private void handleSearchItem(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            try {
                ItemsDTO item = itemsModel.searchItem(itemIdField.getText());
                if(item != null) populateFields(item);
                else new Alert(Alert.AlertType.ERROR, "Item not found!").show();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void updateItem() {
        try {
            ItemsDTO item = new ItemsDTO(
                Integer.parseInt(itemIdField.getText()),
                itemNameField.getText(),
                Integer.parseInt(itemQuantityField.getText()),
                Double.parseDouble(unitPriceField.getText()),
                categoryField.getText(),
                Integer.parseInt(supplierIdField.getText())
            );

            if(itemsModel.updateItem(item)) {
                new Alert(Alert.AlertType.INFORMATION, "Item updated successfully!").show();
                loadItemTable();
                resetFields();
            } else new Alert(Alert.AlertType.ERROR, "Update failed!").show();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteItem() {
        try {
            if(itemsModel.deleteItem(itemIdField.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "Item deleted successfully!").show();
                loadItemTable();
                resetFields();
            } else new Alert(Alert.AlertType.ERROR, "Delete failed!").show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void resetFields() {
        itemIdField.clear();
        itemNameField.clear();
        itemQuantityField.clear();
        unitPriceField.clear();
        categoryField.clear();
        supplierIdField.clear();
    }

    private void loadItemTable() {
        try {
            List<ItemsDTO> itemList = itemsModel.getAllItems();
            ObservableList<ItemsDTO> obList = FXCollections.observableArrayList(itemList);
            tableItems.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
