package lk.ijse.inventory_control_system.model;

import lk.ijse.inventory_control_system.dto.DamagedItemDTO;
import lk.ijse.inventory_control_system.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lk.ijse.inventory_control_system.dto.DamagedItemViewDTO;

public class DamagedItemModel {

    public boolean saveDamage(DamagedItemDTO dto) throws SQLException {
        return CrudUtil.execute(
            "INSERT INTO DamagedItems (Item_ID, QuantityDamaged, DateReported, Reason) VALUES (?,?,?,?)",
            dto.getItem_ID(),
            dto.getQuantityDamaged(),
            dto.getDateReported(),
            dto.getReason()
        );
    }

    public boolean updateDamage(DamagedItemDTO dto) throws SQLException {
        return CrudUtil.execute(
            "UPDATE DamagedItems SET Item_ID=?, QuantityDamaged=?, DateReported=?, Reason=? WHERE Damaged_ID=?",
            dto.getItem_ID(),
            dto.getQuantityDamaged(),
            dto.getDateReported(),
            dto.getReason(),
            dto.getDamaged_ID()
        );
    }

    public boolean deleteDamage(String id) throws SQLException {
        return CrudUtil.execute(
            "DELETE FROM DamagedItems WHERE Damaged_ID=?",
            Integer.parseInt(id)
        );
    }

    public DamagedItemDTO searchDamage(String id) throws SQLException {
        ResultSet rs = CrudUtil.execute(
            "SELECT * FROM DamagedItems WHERE Damaged_ID=?",
            Integer.parseInt(id)
        );

        if (rs.next()) {
            return new DamagedItemDTO(
                rs.getInt("Damaged_ID"),
                rs.getInt("Item_ID"),
                rs.getInt("QuantityDamaged"),
                rs.getDate("DateReported"),
                rs.getString("Reason")
            );
        }
        return null;
    }

    public List<DamagedItemDTO> getAllDamages() throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM DamagedItems");
        List<DamagedItemDTO> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new DamagedItemDTO(
                rs.getInt("Damaged_ID"),
                rs.getInt("Item_ID"),
                rs.getInt("QuantityDamaged"),
                rs.getDate("DateReported"),
                rs.getString("Reason")
            ));
        }
        return list;
    }
    
    public List<DamagedItemViewDTO> getAllDamagesWithItemName() throws SQLException {
    ResultSet rs = CrudUtil.execute(
        "SELECT d.Damaged_ID, i.ItemName, d.QuantityDamaged, d.DateReported, d.Reason " +
        "FROM DamagedItems d JOIN Items i ON d.Item_ID = i.Item_ID"
    );

    List<DamagedItemViewDTO> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new DamagedItemViewDTO(
                rs.getInt("Damaged_ID"),
                rs.getString("ItemName"),
                rs.getInt("QuantityDamaged"),
                rs.getDate("DateReported"),
                rs.getString("Reason")
            ));
        }
        return list;
    }
    
    public List<String> getDamagedItemsForCombo() throws SQLException {
        ResultSet rs = CrudUtil.execute(
            "SELECT d.Damaged_ID, i.ItemName " +
            "FROM DamagedItems d JOIN Items i ON d.Item_ID = i.Item_ID"
        );

        List<String> list = new ArrayList<>();
        while (rs.next()) {
            list.add(
                rs.getInt("Damaged_ID") + " - " + rs.getString("ItemName")
            );
        }
        return list;
    }
}
