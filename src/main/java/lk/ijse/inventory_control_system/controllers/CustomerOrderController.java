package lk.ijse.inventory_control_system.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.inventory_control_system.dto.*;
import lk.ijse.inventory_control_system.model.ItemsModel;
import lk.ijse.inventory_control_system.model.CustomerOrderItemModel;
import lk.ijse.inventory_control_system.model.CustomerOrderModel;
import lk.ijse.inventory_control_system.model.CustomersModel;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerOrderController {

    @FXML private ComboBox<CustomerComboDTO> customerComboBox;
    @FXML private ComboBox<ItemComboDTO> itemComboBox;
    @FXML private TextField quantityField;
    @FXML private TextField unitPriceField;
    @FXML private DatePicker orderDatePicker;
    @FXML private TableView<CustomerOrderItemTM> customerOrderTable;
    @FXML private TableColumn<CustomerOrderItemTM, String> colItemName;
    @FXML private TableColumn<CustomerOrderItemTM, Double> colUnitPrice;
    @FXML private TableColumn<CustomerOrderItemTM, Integer> colQty;
    @FXML private TableColumn<CustomerOrderItemTM, Double> colTotalPrice;
    @FXML private TableColumn<CustomerOrderItemTM, Button> colAction;
    @FXML private Label lblTotal;

    private final ObservableList<CustomerOrderItemTM> customerOrderList = FXCollections.observableArrayList();
    private final ItemsModel itemsModel = new ItemsModel();
    private final CustomersModel customerModel = new CustomersModel();
    private final CustomerOrderModel customerOrderModel = new CustomerOrderModel();
    private final CustomerOrderItemModel customerOrderItemModel = new CustomerOrderItemModel();

    @FXML
    public void initialize() {
        loadItemComboBox();
        loadCustomerComboBox();

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
        customerOrderTable.setItems(customerOrderList);

        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Remove");
            {
                btn.setOnAction(event -> {
                    CustomerOrderItemTM tm = getTableView().getItems().get(getIndex());
                    customerOrderList.remove(tm);
                    updateTotal();
                });
            }
            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if(empty) setGraphic(null);
                else setGraphic(btn);
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

    private void loadCustomerComboBox() {
        try {
            customerComboBox.setItems(FXCollections.observableArrayList(customerModel.getCustomersForCombo()));
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot load customers!").show();
        }
    }

    @FXML
    private void addItemToTable() {
        ItemComboDTO selectedItem = itemComboBox.getValue();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "Select an item!").show();
            return;
        }

        int qty;
        double price;
        try {
            qty = Integer.parseInt(quantityField.getText());
            if (qty <= 0) throw new NumberFormatException();
            price = Double.parseDouble(unitPriceField.getText());
            if (price <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Quantity and Unit Price must be positive numbers!").show();
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

        Optional<CustomerOrderItemTM> existing = customerOrderList.stream()
                .filter(tm -> tm.getItemID() == selectedItem.getItemID())
                .findFirst();

        if (existing.isPresent()) {
            CustomerOrderItemTM tm = existing.get();
            tm.setQty(tm.getQty() + qty);
            tm.setTotalPrice(tm.getQty() * tm.getUnitPrice());
            customerOrderTable.refresh();
        } else {
            customerOrderList.add(new CustomerOrderItemTM(selectedItem.getItemID(), itemName, price, qty, qty * price));
        }

        updateTotal();
        quantityField.clear();
        unitPriceField.clear();
    }

    private void updateTotal() {
        double total = customerOrderList.stream().mapToDouble(CustomerOrderItemTM::getTotalPrice).sum();
        lblTotal.setText(String.format("%.2f", total));
    }

    @FXML
    private void resetFields() {
        customerComboBox.setValue(null);
        itemComboBox.setValue(null);
        quantityField.clear();
        unitPriceField.clear();
        orderDatePicker.setValue(java.time.LocalDate.now());
    }

    @FXML
    private void placeCustomerOrder() {
        if (customerOrderList.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "No items added!").show();
            return;
        }

        CustomerComboDTO selectedCustomer = customerComboBox.getValue();
        if (selectedCustomer == null) {
            new Alert(Alert.AlertType.WARNING, "Select a customer!").show();
            return;
        }

        int customerID = selectedCustomer.getCustomerID();
        Date orderDate = Date.valueOf(orderDatePicker.getValue());

        try {
            int cOrderID = customerOrderModel.saveAndGetID(
                new CustomerOrderDTO(selectedCustomer.getCustomerID(), java.sql.Date.valueOf(orderDatePicker.getValue()))
            );

            customerOrderItemModel.saveCustomerOrderItems(
                cOrderID,
                customerOrderList.stream()
                    .map(tm -> new CustomerOrderItemDTO(0, tm.getItemID(), tm.getQty(), tm.getUnitPrice()))
                    .collect(Collectors.toList())
            );

            new Alert(Alert.AlertType.INFORMATION, "Customer order placed successfully!").show();
            customerOrderList.clear();
            updateTotal();
            resetFields();

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to place customer order!").show();
        }
    }
}