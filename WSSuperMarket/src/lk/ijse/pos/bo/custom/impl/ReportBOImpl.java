package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.ReportBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.OrderDAO;
import lk.ijse.pos.entity.Order;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.dto.ReportDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReportBOImpl implements ReportBO {
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    @Override
    public ArrayList<OrderDTO> getAllDailyOrdersTotal() throws SQLException, ClassNotFoundException {
        ArrayList<Order> orders = orderDAO.getAllDailyOrdersTotal();
        ArrayList<OrderDTO> dtos = new ArrayList<>();
        for (Order o:orders
             ) {
            dtos.add(
                    new OrderDTO(
                            o.getOrderDate(),
                            o.getCost()
                    )
            );

        }
        return dtos;
    }

    @Override
    public ArrayList<ReportDTO> getALLMonthlyOrdersIncome() throws SQLException, ClassNotFoundException {
        ArrayList<ReportDTO>reports=orderDAO.getALLMonthlyOrdersIncome();
        ArrayList<ReportDTO>dtos = new ArrayList<>();
        for (ReportDTO r:reports
             ) {
            dtos.add(
                    new ReportDTO(
                            r.getDate1(),
                            r.getDate2(),
                            r.getTotal()
                    )
            );
        }
        return dtos;
    }

    @Override
    public ArrayList<ReportDTO> getALLAnnuallyOrdersIncome() throws SQLException, ClassNotFoundException {
        ArrayList<ReportDTO>reports=orderDAO.getALLAnnuallyOrdersIncome();
        ArrayList<ReportDTO>dtos = new ArrayList<>();
        for (ReportDTO r:reports
        ) {
            dtos.add(
                    new ReportDTO(
                            r.getDate1(),
                            r.getTotal()
                    )
            );
        }
        return dtos;
    }
}
