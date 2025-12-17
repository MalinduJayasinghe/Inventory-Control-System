package lk.ijse.inventory_control_system.dto;

import java.sql.Date;

public class DamagedItemViewDTO {

    private int damagedID;
    private String itemName;
    private int quantityDamaged;
    private Date dateReported;
    private String reason;

    public DamagedItemViewDTO(int damagedID, String itemName,int quantityDamaged, Date dateReported, String reason) {
        
        this.damagedID = damagedID;
        this.itemName = itemName;
        this.quantityDamaged = quantityDamaged;
        this.dateReported = dateReported;
        this.reason = reason;
    }

    public int getDamagedID() {
        return damagedID;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantityDamaged() {
        return quantityDamaged;
    }

    public Date getDateReported() {
        return dateReported;
    }

    public String getReason() {
        return reason;
    }
}