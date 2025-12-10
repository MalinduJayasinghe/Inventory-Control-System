package lk.ijse.inventory_control_system.dto;


public class BundleOfferDTO {
    
    private int bundleId;
    private int itemId;
    private double discountRate;
    private int itemQuantity;

    public BundleOfferDTO() {
    }

    public BundleOfferDTO(int bundleId, int itemId, double discountRate, int itemQuantity) {
        this.bundleId = bundleId;
        this.itemId = itemId;
        this.discountRate = discountRate;
        this.itemQuantity = itemQuantity;
    }

    public int getBundleId() {
        return bundleId;
    }

    public void setBundleId(int bundleId) {
        this.bundleId = bundleId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(double discountRate) {
        this.discountRate = discountRate;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @Override
    public String toString() {
        return "BundleOfferDTO{" +
                "bundleId=" + bundleId +
                ", itemId=" + itemId +
                ", discountRate=" + discountRate +
                ", itemQuantity=" + itemQuantity +
                '}';
    }
}

