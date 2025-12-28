package lk.ijse.inventory_control_system.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.inventory_control_system.dto.DamagedItemDTO;
import lk.ijse.inventory_control_system.dto.DamagedItemViewDTO;
import lk.ijse.inventory_control_system.dto.ItemComboDTO;
import lk.ijse.inventory_control_system.model.DamagedItemModel;
import lk.ijse.inventory_control_system.model.ItemsModel;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class DamagedItemController {

    @FXML private ComboBox<Integer> damagedIDComboBox;
    @FXML private ComboBox<ItemComboDTO> itemComboBox;
    @FXML private TextField quantityField;
    @FXML private DatePicker datePicker;
    @FXML private TextField reasonField;
    @FXML private TableView<DamagedItemViewDTO> damagedTable;
    @FXML private TableColumn<DamagedItemViewDTO, Integer> colDamagedID;
    @FXML private TableColumn<DamagedItemViewDTO, String> colItemName;
    @FXML private TableColumn<DamagedItemViewDTO, Integer> colQuantity;
    @FXML private TableColumn<DamagedItemViewDTO, Date> colDate;
    @FXML private TableColumn<DamagedItemViewDTO, String> colReason;
    @FXML private ComboBox<String> damagedItemComboBox;

    private final DamagedItemModel damagedItemsModel = new DamagedItemModel();
    private final ItemsModel itemsModel = new ItemsModel();

    @FXML
    public void initialize() {
        setupTable();
        loadDamagedTable();
        loadItemComboBox();
        loadDamagedItemComboBox();
        datePicker.setValue(LocalDate.now());
        
        damagedTable.getSelectionModel().selectedItemProperty().addListener((obs, old, selected) -> {
            if (selected != null) {
                damagedItemComboBox.getItems().stream()
                    .filter(item -> item.startsWith(selected.getDamagedID() + " - "))
                    .findFirst()
                    .ifPresent(item -> damagedItemComboBox.getSelectionModel().select(item));
                
                itemComboBox.getItems().stream()
                    .filter(item -> item.getItemName().equals(selected.getItemName()))
                    .findFirst()
                    .ifPresent(item -> itemComboBox.getSelectionModel().select(item));
                
                quantityField.setText(String.valueOf(selected.getQuantityDamaged()));
                datePicker.setValue(selected.getDateReported().toLocalDate());
                reasonField.setText(selected.getReason());
            }
        });
    }

    private void setupTable() {
        colDamagedID.setCellValueFactory(new PropertyValueFactory<>("damagedID"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantityDamaged"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateReported"));
        colReason.setCellValueFactory(new PropertyValueFactory<>("reason"));
    }

    private void loadDamagedTable() {
        try {
            ObservableList<DamagedItemViewDTO> list =
                FXCollections.observableArrayList(
                    damagedItemsModel.getAllDamagesWithItemName()
                );
            damagedTable.setItems(list);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot load damaged items!").show();
        }
    }

    private void loadItemComboBox() {
        try {
            itemComboBox.setItems(
                    FXCollections.observableArrayList(itemsModel.getItemsForCombo())
            );
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot load items!").show();
        }
    }

    private void loadDamagedItemComboBox() {
        try {
            damagedItemComboBox.setItems(
                FXCollections.observableArrayList(
                    damagedItemsModel.getDamagedItemsForCombo()
                )
            );
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Cannot load damaged items!").show();
        }
    }

    @FXML
    private void saveDamage() {
        if (itemComboBox.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select an item!").show();
            return;
        }
        
        if (quantityField.getText().isEmpty() || reasonField.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
            return;
        }
        
        if (datePicker.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a date!").show();
            return;
        }

        try {
            ItemComboDTO selectedItem = itemComboBox.getValue();
            
            int quantity = Integer.parseInt(quantityField.getText());
            if (quantity <= 0) {
                new Alert(Alert.AlertType.WARNING, "Quantity must be a positive number!").show();
                return;
            }

            DamagedItemDTO dto = new DamagedItemDTO(
                    selectedItem.getItemID(),
                    quantity,
                    Date.valueOf(datePicker.getValue()),
                    reasonField.getText()
            );

            if (damagedItemsModel.saveDamage(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Damaged item recorded!").show();
                loadDamagedTable();
                loadDamagedItemComboBox();
                resetFields();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Quantity must be a valid number!").show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error saving damaged item!").show();
        }
    }

    @FXML
    private void updateDamage() {
        if (damagedItemComboBox.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a damaged item to update!").show();
            return;
        }
        
        if (itemComboBox.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select an item!").show();
            return;
        }
        
        if (quantityField.getText().isEmpty() || reasonField.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all fields!").show();
            return;
        }
        
        if (datePicker.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a date!").show();
            return;
        }

        try {
            int quantity = Integer.parseInt(quantityField.getText());
            if (quantity <= 0) {
                new Alert(Alert.AlertType.WARNING, "Quantity must be a positive number!").show();
                return;
            }
            
            String selected = damagedItemComboBox.getValue();
            int damagedID = Integer.parseInt(selected.split(" - ")[0]);

            DamagedItemDTO dto = new DamagedItemDTO(
                    damagedID,
                    itemComboBox.getValue().getItemID(),
                    quantity,
                    Date.valueOf(datePicker.getValue()),
                    reasonField.getText()
            );

            if (damagedItemsModel.updateDamage(dto)) {
                new Alert(Alert.AlertType.INFORMATION, "Damaged item updated!").show();
                loadDamagedTable();
                loadDamagedItemComboBox();
                resetFields();
            }

        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.WARNING, "Quantity must be a valid number!").show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error updating damaged item!").show();
        }
    }

    @FXML
    private void deleteDamage() {
        if (damagedItemComboBox.getValue() == null) {
            new Alert(Alert.AlertType.WARNING, "Please select a damaged item to delete!").show();
            return;
        }

        String selected = damagedItemComboBox.getValue();
        String damagedID = selected.split(" - ")[0];

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Are you sure you want to delete this damaged item record?");
        confirmAlert.setContentText("Damaged Item ID: " + damagedID);

        var result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (damagedItemsModel.deleteDamage(damagedID)) {
                    new Alert(Alert.AlertType.INFORMATION, "Damaged item deleted!").show();
                    loadDamagedTable();
                    loadDamagedItemComboBox();
                    resetFields();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete damaged item!").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error deleting damaged item!").show();
            }
        }
    }

    @FXML
    private void searchDamage() {
        String selected = damagedItemComboBox.getValue();

        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Select a damaged item!").show();
            return;
        }

        try {
            String damagedID = selected.split(" - ")[0];

            DamagedItemDTO dto = damagedItemsModel.searchDamage(damagedID);

            if (dto != null) {
                populateFields(dto);
            } else {
                new Alert(Alert.AlertType.ERROR, "Record not found!").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error searching damaged item!").show();
        }
    }

    private void populateFields(DamagedItemDTO dto) {
        for (String value : damagedItemComboBox.getItems()) {
            int damagedID = Integer.parseInt(value.split(" - ")[0]);
            if (damagedID == dto.getDamaged_ID()) {
                damagedItemComboBox.setValue(value);
                break;
            }
        }

        for (ItemComboDTO item : itemComboBox.getItems()) {
            if (item.getItemID() == dto.getItem_ID()) {
                itemComboBox.setValue(item);
                break;
            }
        }
        
        quantityField.setText(String.valueOf(dto.getQuantityDamaged()));
        datePicker.setValue(dto.getDateReported().toLocalDate());
        reasonField.setText(dto.getReason());
    }

    @FXML
    private void resetFields() {
        damagedIDComboBox.setValue(null);
        damagedItemComboBox.getSelectionModel().clearSelection();
        itemComboBox.getSelectionModel().clearSelection();
        quantityField.clear();
        reasonField.clear();
        datePicker.setValue(LocalDate.now());
    }
}