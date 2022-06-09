package lk.ijse.pos.controller;

import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.custom.ReportBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.dto.ReportDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReportFormController {
    public JFXComboBox cmbDailyMonthlyAnnually;
    public JFXButton btnSearch;
    public LineChart lineChart;
    private final ReportBO reportBO = (ReportBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.REPORT);


    public void initialize(){

        setReportType();
        btnSearch.setDisable(true);
        cmbDailyMonthlyAnnually.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                btnSearch.setDisable(false);
            }
        });


    }

    private void setReportType() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.add("Daily");
        obList.add("Monthly");
        obList.add("Annually");
        cmbDailyMonthlyAnnually.setItems(obList);
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
       if (cmbDailyMonthlyAnnually.getValue() =="Daily"){

           try {
               lineChart.getData().clear();
               XYChart.Series series = new XYChart.Series();
               ArrayList<OrderDTO> allDailyOrdersTotal = reportBO.getAllDailyOrdersTotal();
               for (OrderDTO order:allDailyOrdersTotal
                ) {
                   series.getData().add(new XYChart.Data<>(String.valueOf(order.getOrderDate()),order.getCost()));

               }

               series.setName("Daily Income");
               lineChart.getData().add(series);

           } catch (SQLException throwables) {
               throwables.printStackTrace();
           } catch (ClassNotFoundException e) {
               e.printStackTrace();
           }



       }else if (cmbDailyMonthlyAnnually.getValue() =="Monthly"){

           try {
               lineChart.getData().clear();
               XYChart.Series series = new XYChart.Series();
               ArrayList<ReportDTO> allDailyOrdersTotal = reportBO.getALLMonthlyOrdersIncome();
               for (ReportDTO order:allDailyOrdersTotal
               ) {
                   series.getData().add(new XYChart.Data<>(order.getDate1() +"-"+ order.getDate2(),order.getTotal()));

               }

               series.setName("Monthly Income");
               lineChart.getData().add(series);

           } catch (SQLException throwables) {
               throwables.printStackTrace();
           } catch (ClassNotFoundException e) {
               e.printStackTrace();
           }


       }else if (cmbDailyMonthlyAnnually.getValue() =="Annually"){

           try {
               lineChart.getData().clear();
               XYChart.Series series = new XYChart.Series();
               ArrayList<ReportDTO> allDailyOrdersTotal = reportBO.getALLAnnuallyOrdersIncome();
               for (ReportDTO order:allDailyOrdersTotal
               ) {
                   series.getData().add(new XYChart.Data<>(order.getDate1(),order.getTotal()));

               }

               series.setName("Annually Income");
               lineChart.getData().add(series);

           } catch (SQLException throwables) {
               throwables.printStackTrace();
           } catch (ClassNotFoundException e) {
               e.printStackTrace();
           }
       }
    }
}
