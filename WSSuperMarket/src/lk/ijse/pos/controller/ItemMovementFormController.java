package lk.ijse.pos.controller;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.custom.ItemMovementBO;
import lk.ijse.pos.dto.OrderDetailDTO;


import java.sql.SQLException;
import java.util.ArrayList;

public class ItemMovementFormController {
    public LineChart lineChart;
    private final ItemMovementBO itemMovementBO = (ItemMovementBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.ITEM_MOVEMENT);
    public void initialize(){


        setChart();
    }

    private void setChart() {
        try {
            lineChart.getData().clear();
            XYChart.Series series = new XYChart.Series();
            ArrayList<OrderDetailDTO> allDailyOrdersTotal = itemMovementBO.getAllSellItemsCountChart();
            for (OrderDetailDTO items:allDailyOrdersTotal
            ) {
                series.getData().add(new XYChart.Data<>(items.getItemCode(),items.getOrderQty()));

            }

            series.setName("Movable Items ");
            lineChart.getData().add(series);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
