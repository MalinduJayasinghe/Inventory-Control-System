package lk.ijse.inventory_control_system.model;

import lk.ijse.inventory_control_system.dto.PurchaseOrderItemDTO;
import lk.ijse.inventory_control_system.util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class PurchaseOrderItemModel {

    private final ItemsModel itemsModel = new ItemsModel();

    public boolean savePurchaseOrderItems(List<PurchaseOrderItemDTO> itemList, int pOrderID) throws SQLException {

        for (PurchaseOrderItemDTO item : itemList) {

            boolean result = CrudUtil.execute(
                    "INSERT INTO PurchaseOrderItem (p_order_id, item_id, qty, unit_price) VALUES (?, ?, ?, ?)",
                    pOrderID,
                    item.getItemID(),
                    item.getQty(),
                    item.getUnitPrice()
            );

            if (result) {
                boolean stockUpdated = itemsModel.increaseItemQty(item.getItemID(), item.getQty());
                if (!stockUpdated) throw new SQLException();
            } else {
                throw new SQLException();
            }
        }

        return true;
    }
}
