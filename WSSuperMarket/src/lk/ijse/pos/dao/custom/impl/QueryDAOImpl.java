package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.CrudUtil;
import lk.ijse.pos.dao.custom.QueryDAO;
import lk.ijse.pos.entity.CustomEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ArrayList<CustomEntity> SearchOrderDetailsUsingOrderId(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT i.ItemCode,i.Description,i.PackSize,i.UnitPrice,od.OrderQty,od.Discount,od.price FROM Item i JOIN `OrderDetail` od ON i.ItemCode = od.ItemCode WHERE od.orderId =?",id);
        ArrayList<CustomEntity> orders = new ArrayList<>();
        while (rst.next()){
            orders.add(
                    new CustomEntity(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getDouble(4),
                            rst.getInt(5),
                            rst.getDouble(6),
                            rst.getDouble(7)
                    )
            );
        }
        return orders;
    }
}
