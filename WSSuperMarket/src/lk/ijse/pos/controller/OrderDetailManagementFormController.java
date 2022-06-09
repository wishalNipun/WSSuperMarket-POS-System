package lk.ijse.pos.controller;

import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.custom.OrderDetailBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.pos.dto.CustomDTO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.view.tm.OrderDetailTM;
import lk.ijse.pos.view.tm.OrderTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailManagementFormController {
    public JFXComboBox cmbCustomerId;
    public JFXTextField txtAddress;
    public TableView<OrderTM>tblOrders;
    public TableColumn colOrderId;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colCost;
    public JFXTextField txtName;
    public JFXTextField txtTitle;
    public JFXButton btnSearch;
    public TableView tblOrderDetails;
    public TableColumn colItemId;
    public TableColumn colDescription;
    public TableColumn colPackSIze;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colDiscount;
    public TableColumn colTotal;

    private final OrderDetailBO orderDetailBO = (OrderDetailBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.ORDER_DETAIL);

    public void initialize(){

        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        colItemId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSIze.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        btnSearch.setDisable(true);
        txtTitle.setFocusTraversable(false);
        txtTitle.setEditable(false);
        txtName.setFocusTraversable(false);
        txtName.setEditable(false);
        txtAddress.setFocusTraversable(false);
        txtAddress.setEditable(false);
        loadCustomerIds();
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnSearch.setDisable(newValue==null);
            if (newValue != null) {
                try {
                    try {
                        CustomerDTO customerDTO = orderDetailBO.searchCustomerDetail(newValue+"");
                        txtTitle.setText(customerDTO.getTitle());
                        txtName.setText(customerDTO.getName());
                        txtAddress.setText(customerDTO.getAddress());

                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, "Failed to find the customer " + newValue + "" + e).show();
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                txtTitle.clear();
                txtName.clear();
                txtAddress.clear();
            }
        });
        
        tblOrders.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            SelectedOrderDetails(newValue.getOrderId());
        });
    }

    private void SelectedOrderDetails(String orderId) {
        tblOrderDetails.getItems().clear();
        try {
            ArrayList<CustomDTO> customOrders = orderDetailBO.SearchOrderDetailsUsingOrderId(orderId);
            for (CustomDTO c:customOrders
                 ) {
                tblOrderDetails.getItems().add(new OrderDetailTM(c.getCode(),c.getDescription(),c.getPackSize(),c.getQty(),c.getUnitPrice(),c.getDiscount(),c.getPrice()));

            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadCustomerIds() {
        try {
            ArrayList<CustomerDTO> all = orderDetailBO.getAllCustomers();
            for (CustomerDTO customerDTO : all) {
                cmbCustomerId.getItems().add(customerDTO.getId());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void loadCustomerOrders(String id){

        try {
            ArrayList<OrderDTO> allOrders = orderDetailBO.SearchCustomerOrders(id);

            for (OrderDTO order:allOrders
                 ) {

                tblOrders.getItems().add(new OrderTM(order.getOrderId(),order.getOrderDate(),order.getOrderTime(),order.getCost()));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
    public void btnSearchOnAction(ActionEvent actionEvent) {
        tblOrders.getItems().clear();
        loadCustomerOrders((String) cmbCustomerId.getValue());
    }
}
