package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.CrudDAO;
import lk.ijse.pos.entity.Order;
import lk.ijse.pos.dto.ReportDTO;

import java.sql.SQLException;
import java.util.ArrayList;


public interface OrderDAO extends CrudDAO<Order,String> {
    ArrayList<Order> SearchCustomerOrders(String id)throws SQLException, ClassNotFoundException;
    ArrayList<Order> getAllDailyOrdersTotal()throws SQLException,ClassNotFoundException;
    ArrayList<ReportDTO> getALLMonthlyOrdersIncome()throws SQLException,ClassNotFoundException;
    ArrayList<ReportDTO> getALLAnnuallyOrdersIncome()throws SQLException,ClassNotFoundException;
}
