package lk.ijse.inventory_control_system.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lk.ijse.inventory_control_system.dto.PurchaseOrderItemTM;
import lk.ijse.inventory_control_system.model.ItemsModel;
import lk.ijse.inventory_control_system.model.PurchaseOrderItemModel;
import java.sql.SQLException;
import java.util.stream.Collectors;
import lk.ijse.inventory_control_system.dto.ItemComboDTO;
import lk.ijse.inventory_control_system.dto.SupplierComboDTO;
import lk.ijse.inventory_control_system.model.SuppliersModel;

public class PurchaseOrderController {

    @FXML private ComboBox<SupplierComboDTO> supplierComboBox;
    @FXML private ComboBox<ItemComboDTO> itemComboBox;
    @FXML private TextField quantityField;
    @FXML private TextField unitPriceField;
    @FXML private DatePicker orderDatePicker;
    @FXML private TableView<PurchaseOrderItemTM> purchaseOrderTable;
    @FXML private TableColumn<PurchaseOrderItemTM, String> colItemName;
    @FXML private TableColumn<PurchaseOrderItemTM, Double> colUnitPrice;
    @FXML private TableColumn<PurchaseOrderItemTM, Integer> colQty;
    @FXML private TableColumn<PurchaseOrderItemTM, Double> colTotalPrice;
    @FXML private TableColumn<PurchaseOrderItemTM, Button> colAction;
    @FXML private Label lblTotal;

    private final ObservableList<PurchaseOrderItemTM> purchaseOrderList = FXCollections.observableArrayList();
    private final PurchaseOrderItemModel purchaseOrderItemModel = new PurchaseOrderItemModel();
    private final ItemsModel itemsModel = new ItemsModel();
    private final SuppliersModel suppliersModel = new SuppliersModel();

    @FXML
    public void initialize() {
        loadItemComboBox();
        itemComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                try {
                    // Get the unit price from your ItemsModel
                    double price = itemsModel.getItemPriceByID(newItem.getItemID());
                    unitPriceField.setText(String.valueOf(price));
                } catch (SQLException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Cannot load item price!").show();
                }
            } else {
                unitPriceField.clear();
            }
        });
        loadSupplierComboBox();
        orderDatePicker.setValue(java.time.LocalDate.now());
        purchaseOrderTable.setItems(purchaseOrderList);
    }

    private void loadItemComboBox() {
        try {
            itemComboBox.setItems(FXCollections.observableArrayList(itemsModel.getItemsForCombo()));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot load items").show();
        }
    }
    
    private void loadSupplierComboBox() {
        try {
            supplierComboBox.setItems(FXCollections.observableArrayList(suppliersModel.getSuppliersForCombo()));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot load suppliers!").show();
        }
    }

    @FXML
    private void addItemToTable() {
        try {
            ItemComboDTO selectedItem = itemComboBox.getValue(); 
            if (selectedItem == null) {
                new Alert(Alert.AlertType.WARNING, "Select an item!").show();
                return;
            }

            int itemID = selectedItem.getItemID(); 
            int qty = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(unitPriceField.getText());
            String itemName = itemsModel.getItemNameByID(itemID);

            double total = qty * price;
            PurchaseOrderItemTM tm = new PurchaseOrderItemTM(itemID, itemName, price, qty, total);

            purchaseOrderList.add(tm);
            updateTotal();

            quantityField.clear();
            unitPriceField.clear();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.WARNING, "Invalid input!").show();
        }
    }

    private void updateTotal() {
        double total = purchaseOrderList.stream().mapToDouble(PurchaseOrderItemTM::getTotalPrice).sum();
        lblTotal.setText(String.valueOf(total));
    }

    @FXML
    private void resetFields() {
        supplierComboBox.setValue(null);
        itemComboBox.setValue(null);
        quantityField.clear();
        unitPriceField.clear();
        orderDatePicker.setValue(java.time.LocalDate.now());
    }

    @FXML
    private void placePurchaseOrder() {
        if (purchaseOrderList.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "No items added!").show();
            return;
        }

        try {
            SupplierComboDTO selectedSupplier = supplierComboBox.getValue();
            if (selectedSupplier == null) {
                new Alert(Alert.AlertType.WARNING, "Select a supplier!").show();
                return;
            }

            int supplierID = selectedSupplier.getSupplierID();
            int pOrderID = 1; 
            
            purchaseOrderItemModel.savePurchaseOrderItems(
                    purchaseOrderList.stream().map(tm ->
                            new lk.ijse.inventory_control_system.dto.PurchaseOrderItemDTO(
                                    pOrderID, tm.getItemID(), tm.getQty(), tm.getUnitPrice())
                    ).collect(Collectors.toList()),
                    pOrderID
            );

            new Alert(Alert.AlertType.INFORMATION, "Purchase order placed successfully!").show();
            purchaseOrderList.clear();
            updateTotal();
            resetFields();

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to place purchase order!").show();
        }
    }
}
