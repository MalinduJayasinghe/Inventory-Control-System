package lk.ijse.inventory_control_system.controllers;

import java.io.InputStream;
import java.sql.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.inventory_control_system.dto.*;
import lk.ijse.inventory_control_system.model.ItemsModel;
import lk.ijse.inventory_control_system.model.PurchaseOrderItemModel;
import lk.ijse.inventory_control_system.model.PurchaseOrderModel;
import lk.ijse.inventory_control_system.model.SuppliersModel;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;
import lk.ijse.inventory_control_system.db.DBConnection;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

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
    private final ItemsModel itemsModel = new ItemsModel();
    private final SuppliersModel suppliersModel = new SuppliersModel();
    private final PurchaseOrderModel purchaseOrderModel = new PurchaseOrderModel();
    private final PurchaseOrderItemModel purchaseOrderItemModel = new PurchaseOrderItemModel();

    @FXML
    public void initialize() {
        loadItemComboBox();
        loadSupplierComboBox();

        itemComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                try {
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

        orderDatePicker.setValue(java.time.LocalDate.now());
        purchaseOrderTable.setItems(purchaseOrderList);

        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Remove");
            {
                btn.setOnAction(event -> {
                    PurchaseOrderItemTM tm = getTableView().getItems().get(getIndex());
                    
                    Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmAlert.setTitle("Confirm Remove");
                    confirmAlert.setHeaderText("Remove item from order?");
                    confirmAlert.setContentText("Item: " + tm.getItemName());
                    
                    var result = confirmAlert.showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        purchaseOrderList.remove(tm);
                        updateTotal();
                    }
                });
            }
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if(empty) setGraphic(null);
                else setGraphic(btn);
            }
        });
        
        purchaseOrderTable.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) {
                itemComboBox.getItems().stream()
                    .filter(item -> item.getItemID() == selected.getItemID())
                    .findFirst()
                    .ifPresent(item -> itemComboBox.getSelectionModel().select(item));
                
                quantityField.setText(String.valueOf(selected.getQty()));
                unitPriceField.setText(String.valueOf(selected.getUnitPrice()));
            }
        });
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
        ItemComboDTO selectedItem = itemComboBox.getValue();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "Select an item!").show();
            return;
        }
        
        if (quantityField.getText().isEmpty() || unitPriceField.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter quantity and unit price!").show();
            return;
        }

        int qty;
        double price;
        try {
            qty = Integer.parseInt(quantityField.getText());
            if (qty <= 0) {
                new Alert(Alert.AlertType.WARNING, "Quantity must be a positive number!").show();
                return;
            }
            price = Double.parseDouble(unitPriceField.getText());
            if (price <= 0) {
                new Alert(Alert.AlertType.WARNING, "Unit price must be a positive number!").show();
                return;
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Quantity and Unit Price must be valid numbers!").show();
            return;
        }

        String itemName;
        try {
            itemName = itemsModel.getItemNameByID(selectedItem.getItemID());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot retrieve item name!").show();
            return;
        }

        Optional<PurchaseOrderItemTM> existing = purchaseOrderList.stream()
                .filter(tm -> tm.getItemID() == selectedItem.getItemID())
                .findFirst();

        if (existing.isPresent()) {
            PurchaseOrderItemTM tm = existing.get();
            tm.setQty(tm.getQty() + qty);
            tm.setTotalPrice(tm.getQty() * tm.getUnitPrice());
            purchaseOrderTable.refresh();
            new Alert(Alert.AlertType.INFORMATION, "Item quantity updated in the order!").show();
        } else {
            purchaseOrderList.add(new PurchaseOrderItemTM(selectedItem.getItemID(), itemName, price, qty, qty * price));
            new Alert(Alert.AlertType.INFORMATION, "Item added to order!").show();
        }

        updateTotal();
        itemComboBox.getSelectionModel().clearSelection();
        quantityField.clear();
        unitPriceField.clear();
    }

    private void updateTotal() {
        double total = purchaseOrderList.stream().mapToDouble(PurchaseOrderItemTM::getTotalPrice).sum();
        lblTotal.setText(String.format("%.2f", total));
    }

    @FXML
    private void resetFields() {
        supplierComboBox.getSelectionModel().clearSelection();
        itemComboBox.getSelectionModel().clearSelection();
        quantityField.clear();
        unitPriceField.clear();
        orderDatePicker.setValue(java.time.LocalDate.now());
        purchaseOrderList.clear();
        updateTotal();
    }

    @FXML
    private void placePurchaseOrder() {
        if (purchaseOrderList.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "No items added to the order!").show();
            return;
        }

        SupplierComboDTO selectedSupplier = supplierComboBox.getValue();
        if (selectedSupplier == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a supplier!").show();
            return;
        }
        
        if (orderDatePicker.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select an order date!").show();
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Purchase Order");
        confirmAlert.setHeaderText("Place this purchase order?");
        confirmAlert.setContentText(
            "Supplier: " + selectedSupplier.getSupplierName() + "\n" +
            "Total Items: " + purchaseOrderList.size() + "\n" +
            "Total Amount: " + lblTotal.getText()
        );

        var result = confirmAlert.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            return;
        }

        try {
            int pOrderID = purchaseOrderModel.saveAndGetID(
                new PurchaseOrderDTO(selectedSupplier.getSupplierID(), java.sql.Date.valueOf(orderDatePicker.getValue()))
            );

            purchaseOrderItemModel.savePurchaseOrderItems(
                pOrderID,
                purchaseOrderList.stream()
                    .map(tm -> new PurchaseOrderItemDTO(0, tm.getItemID(), tm.getQty(), tm.getUnitPrice()))
                    .collect(Collectors.toList())
            );

            new Alert(Alert.AlertType.INFORMATION, "Purchase order placed successfully!\nOrder ID: " + pOrderID).show();

            try {
                printInvoice(pOrderID);
            } catch (JRException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.WARNING, "Order saved but failed to print invoice!").show();
            }

            purchaseOrderList.clear();
            updateTotal();
            resetFields();

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to place purchase order!\n" + e.getMessage()).show();
        }
    }

    public void printInvoice(int orderId) throws SQLException, JRException {
        Connection conn = DBConnection.getInstance().getConnection();

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("orderId", orderId);

        InputStream inputStream = getClass().getResourceAsStream("/lk/ijse/inventory_control_system/reports/purchaseOrder.jrxml");
        
        if (inputStream == null) {
            throw new JRException("Report file not found!");
        }
        
        JasperReport jr = JasperCompileManager.compileReport(inputStream);
        JasperPrint jp = JasperFillManager.fillReport(jr, parameters, conn);
        JasperViewer.viewReport(jp, false);
    }
}