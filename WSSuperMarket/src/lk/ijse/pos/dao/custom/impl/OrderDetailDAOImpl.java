package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.CrudUtil;
import lk.ijse.pos.dao.custom.OrderDetailDAO;
import lk.ijse.pos.entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean insert(OrderDetail dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `OrderDetail` VALUES (?,?,?,?,?)", dto.getOrderId(), dto.getItemCode(), dto.getQty(), dto.getPrice(),dto.getDiscount());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return  CrudUtil.executeUpdate("DELETE FROM `OrderDetail` WHERE OrderId=?", id);
    }

    @Override
    public boolean update(OrderDetail dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetail search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<OrderDetail> getAllSellItemsCountChart() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT ItemCode, SUM(OrderQty)  FROM `orderdetail` GROUP BY ItemCode ORDER BY SUM(OrderQty) DESC ");
        ArrayList<OrderDetail> count = new ArrayList<>();
        while (rst.next()){
            count.add(
                    new OrderDetail(
                            rst.getString(1),
                            rst.getInt(2)
                    )
            );
        }
        return count;
    }
}
