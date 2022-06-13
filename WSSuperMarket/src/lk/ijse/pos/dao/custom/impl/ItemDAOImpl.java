package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.CrudUtil;
import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item");
        ArrayList<Item> allItems = new ArrayList<>();
        while (rst.next()) {
            allItems.add(new Item(rst.getString(1), rst.getString(2), rst.getString(3), rst.getDouble(4), rst.getInt(5)));
        }
        return allItems;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {

        ResultSet rst = CrudUtil.executeQuery("SELECT ItemCode FROM Item ORDER BY ItemCode DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("ItemCode");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }
    }

    @Override
    public boolean insert(Item entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Item VALUES(?,?,?,?,?)", entity.getCode(), entity.getDescription(), entity.getPackSize(), entity.getUnitPrice(), entity.getQtyOnHand());
    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Item WHERE ItemCode=?", code);
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET Description=?,PackSize=?,UnitPrice=?,QtyOnHand=? WHERE ItemCode=?", entity.getDescription(), entity.getPackSize(), entity.getUnitPrice(), entity.getQtyOnHand(), entity.getCode());
    }

    @Override
    public boolean exist(String code) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT ItemCode FROM Item WHERE ItemCode=?", code).next();
    }

    @Override
    public Item search(String code) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Item WHERE ItemCode=?", code);
        rst.next();
        return new Item(
                code,
                rst.getString(2),
                rst.getString(3),
                rst.getDouble(4),
                rst.getInt(5)
        );
    }

    @Override
    public boolean updateItemQtyUsingItemCode(Item dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Item SET QtyOnHand=? WHERE ItemCode=?", dto.getQtyOnHand(), dto.getCode());
    }

}
