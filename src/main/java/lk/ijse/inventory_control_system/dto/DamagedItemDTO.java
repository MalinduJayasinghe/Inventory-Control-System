package lk.ijse.inventory_control_system.dto;

import java.sql.Date;

public class DamagedItemDTO {

    private int Damaged_ID;
    private int Item_ID;
    private int QuantityDamaged;
    private Date DateReported;
    private String Reason;

    public DamagedItemDTO(int damaged_ID, int item_ID, int quantityDamaged, Date dateReported, String reason) {
        this.Damaged_ID = damaged_ID;
        this.Item_ID = item_ID;
        this.QuantityDamaged = quantityDamaged;
        this.DateReported = dateReported;
        this.Reason = reason;
    }

    public DamagedItemDTO(int item_ID, int quantityDamaged, Date dateReported, String reason) {
        this.Item_ID = item_ID;
        this.QuantityDamaged = quantityDamaged;
        this.DateReported = dateReported;
        this.Reason = reason;
    }

    public int getDamaged_ID() { return Damaged_ID; }
    public void setDamaged_ID(int damaged_ID) { Damaged_ID = damaged_ID; }

    public int getItem_ID() { return Item_ID; }
    public void setItem_ID(int item_ID) { Item_ID = item_ID; }

    public int getQuantityDamaged() { return QuantityDamaged; }
    public void setQuantityDamaged(int quantityDamaged) { QuantityDamaged = quantityDamaged; }

    public Date getDateReported() { return DateReported; }
    public void setDateReported(Date dateReported) { DateReported = dateReported; }

    public String getReason() { return Reason; }
    public void setReason(String reason) { Reason = reason; }
}
