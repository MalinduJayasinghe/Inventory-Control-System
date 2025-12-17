package lk.ijse.inventory_control_system.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lk.ijse.inventory_control_system.dto.ItemsDTO;
import lk.ijse.inventory_control_system.dto.ItemComboDTO;
import lk.ijse.inventory_control_system.util.CrudUtil;

public class ItemsModel {

    public boolean saveItem(ItemsDTO item) throws SQLException {
        return CrudUtil.execute(
            "INSERT INTO Items (ItemName, ItemQuantity, UnitPrice, Category, Supplier_ID) VALUES (?,?,?,?,?)",
            item.getItemName(),
            item.getItemQuantity(),
            item.getUnitPrice(),
            item.getCategory(),
            item.getSupplierID()
        );
    }

    public ItemsDTO searchItem(String id) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Items WHERE Item_ID=?", Integer.parseInt(id));
        ItemsDTO item = null;
        if (rs.next()) {
            item = new ItemsDTO(
                rs.getInt("Item_ID"),
                rs.getString("ItemName"),
                rs.getInt("ItemQuantity"),
                rs.getDouble("UnitPrice"),
                rs.getString("Category"),
                rs.getInt("Supplier_ID")
            );
        }
        return item;
    }

    public boolean updateItem(ItemsDTO item) throws SQLException {
        return CrudUtil.execute(
            "UPDATE Items SET ItemName=?, ItemQuantity=?, UnitPrice=?, Category=?, Supplier_ID=? WHERE Item_ID=?",
            item.getItemName(),
            item.getItemQuantity(),
            item.getUnitPrice(),
            item.getCategory(),
            item.getSupplierID(),
            item.getItemID()
        );
    }

    public boolean deleteItem(String id) throws SQLException {
        return CrudUtil.execute("DELETE FROM Items WHERE Item_ID=?", Integer.parseInt(id));
    }

    public List<ItemsDTO> getAllItems() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM Items");
        List<ItemsDTO> itemList = new ArrayList<>();

        while (rs.next()) {
            ItemsDTO item = new ItemsDTO(
                rs.getInt("Item_ID"),
                rs.getString("ItemName"),
                rs.getInt("ItemQuantity"),
                rs.getDouble("UnitPrice"),
                rs.getString("Category"),
                rs.getInt("Supplier_ID")
            );
            itemList.add(item);
        }
        return itemList;
    }
    
    public List<ItemComboDTO> getItemsForCombo() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT Item_ID, ItemName FROM Items");
        List<ItemComboDTO> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new ItemComboDTO(
                rs.getInt("Item_ID"),
                rs.getString("ItemName")
            ));
        }
        return list;
    }
    
    public String getItemNameByID(int itemID) throws SQLException {
        ResultSet rs = CrudUtil.execute(
            "SELECT ItemName FROM Items WHERE Item_ID=?",
            itemID
        );
        if (rs.next()) {
            return rs.getString("ItemName");
        }
        return "Unknown";
    }
}
