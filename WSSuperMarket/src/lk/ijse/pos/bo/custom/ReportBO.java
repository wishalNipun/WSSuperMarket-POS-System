package lk.ijse.pos.bo.custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.dto.ReportDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ReportBO extends SuperBO {
    ArrayList<OrderDTO> getAllDailyOrdersTotal()throws SQLException,ClassNotFoundException;
    ArrayList<ReportDTO> getALLMonthlyOrdersIncome()throws SQLException,ClassNotFoundException;
    ArrayList<ReportDTO> getALLAnnuallyOrdersIncome()throws SQLException,ClassNotFoundException;
}
