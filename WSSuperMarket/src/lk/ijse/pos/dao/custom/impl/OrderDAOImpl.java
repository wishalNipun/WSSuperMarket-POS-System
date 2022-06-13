package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.CrudUtil;
import lk.ijse.pos.dao.custom.OrderDAO;
import lk.ijse.pos.entity.Order;

import lk.ijse.pos.dto.ReportDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT OrderId FROM `Order` ORDER BY OrderId DESC LIMIT 1;");

        return rst.next() ? String.format("OID-%03d", (Integer.parseInt(rst.getString("OrderId").replace("OID-", "")) + 1)) : "OID-001";

    }

    @Override
    public boolean exist(String oid) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT OrderId FROM `Order` WHERE OrderId=?", oid);
        return rst.next();
    }

    @Override
    public boolean insert(Order dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `Order` VALUES(?,?,?,?,?)", dto.getOrderId(), dto.getCustomerId(),dto.getOrderDate(),dto.getOrderTime(),dto.getCost());

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
     return CrudUtil.executeUpdate("DELETE FROM `Order` WHERE OrderId=?", id);

    }

    @Override
    public boolean update(Order dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order search(String oid) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Order> SearchCustomerOrders(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT OrderId,OrderDate,Time,Cost FROM `Order` WHERE CustID=?", id);
        ArrayList<Order> orders = new ArrayList<>();
        while (rst.next()){
            orders.add(
                    new Order(
                            rst.getString(1),
                            LocalDate.parse(rst.getString(2)),
                            LocalTime.parse(rst.getString(3)),
                            rst.getDouble(4)

                    )
            );
        }

        return orders;

    }

    @Override
    public ArrayList<Order> getAllDailyOrdersTotal() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT OrderDate, SUM(Cost) AS 'Sales' FROM `Order` GROUP BY OrderDate");
        ArrayList<Order> daily = new ArrayList<>();
        while (rst.next()){
            daily.add(
                    new Order(
                            LocalDate.parse(rst.getString(1)),
                            rst.getDouble(2)
                    )
            );
        }
        return daily;
    }

    @Override
    public ArrayList<ReportDTO> getALLMonthlyOrdersIncome() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT YEAR(OrderDate), MONTH(OrderDate), SUM(Cost) AS Income FROM `Order` GROUP BY YEAR(OrderDate), MONTH(OrderDate) ORDER BY YEAR(OrderDate), MONTH(OrderDate)");
        ArrayList<ReportDTO> monthly = new ArrayList<>();
        while (rst.next()){
            monthly.add(
                    new ReportDTO(
                            rst.getString(1),
                            rst.getString(2),
                            rst.getDouble(3)
                    )
            );
        }
        return monthly;
    }

    @Override
    public ArrayList<ReportDTO> getALLAnnuallyOrdersIncome() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT YEAR(OrderDate), SUM(Cost) AS 'Sales' FROM `Order` GROUP BY YEAR(OrderDate)");
        ArrayList<ReportDTO> annually = new ArrayList<>();
        while (rst.next()){
            annually.add(
                    new ReportDTO(
                            rst.getString(1),
                            rst.getDouble(2)
                    )
            );
        }
        return annually;
    }
}
