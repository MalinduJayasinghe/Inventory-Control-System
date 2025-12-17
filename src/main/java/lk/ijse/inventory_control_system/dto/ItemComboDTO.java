package lk.ijse.inventory_control_system.dto;

public class ItemComboDTO {
    private int itemID;
    private String itemName;

    public ItemComboDTO(int itemID, String itemName) {
        this.itemID = itemID;
        this.itemName = itemName;
    }

    public int getItemID() {
        return itemID;
    }

    @Override
    public String toString() {
        return itemID + " - " + itemName;
    }
}
