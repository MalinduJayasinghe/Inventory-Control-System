package lk.ijse.inventory_control_system.dto;

public class DashboardDTO {
    private int totalItems;
    private int lowStockItems;
    private int totalOrders;
    private int damagedItems;

    public DashboardDTO() {
    }

    public DashboardDTO(int totalItems, int lowStockItems, int totalOrders, int damagedItems) {
        this.totalItems = totalItems;
        this.lowStockItems = lowStockItems;
        this.totalOrders = totalOrders;
        this.damagedItems = damagedItems;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getLowStockItems() {
        return lowStockItems;
    }

    public void setLowStockItems(int lowStockItems) {
        this.lowStockItems = lowStockItems;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public int getDamagedItems() {
        return damagedItems;
    }

    public void setDamagedItems(int damagedItems) {
        this.damagedItems = damagedItems;
    }

    @Override
    public String toString() {
        return "DashboardDTO{" +
                "totalItems=" + totalItems +
                ", lowStockItems=" + lowStockItems +
                ", totalOrders=" + totalOrders +
                ", damagedItems=" + damagedItems +
                '}';
    }
}