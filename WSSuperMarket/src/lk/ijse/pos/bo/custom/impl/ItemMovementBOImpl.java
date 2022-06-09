package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.ItemMovementBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.OrderDetailDAO;
import lk.ijse.pos.entity.OrderDetail;
import lk.ijse.pos.dto.OrderDetailDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemMovementBOImpl implements ItemMovementBO {
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);
    @Override
    public ArrayList<OrderDetailDTO> getAllSellItemsCountChart() throws SQLException, ClassNotFoundException {

        ArrayList<OrderDetail> count = orderDetailDAO.getAllSellItemsCountChart();
        ArrayList<OrderDetailDTO> allItemsChart = new ArrayList<>();

        for (OrderDetail chart : count) {
            allItemsChart.add(new OrderDetailDTO(
                    chart.getItemCode(),
                    chart.getQty()
            ));
        }
        return allItemsChart;

    }

}
