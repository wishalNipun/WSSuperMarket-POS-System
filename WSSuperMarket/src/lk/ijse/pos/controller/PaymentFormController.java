package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.custom.PurchaseOrderBO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.dto.OrderDetailDTO;
import lk.ijse.pos.view.tm.OrderDetailTM;



import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PaymentFormController {
    public JFXTextField txtName;
    public JFXTextField txtTitle;
    public TableColumn colDelete;
    public JFXButton btnAddToCart;
    public TableView<OrderDetailTM> tblPayment;
    public JFXComboBox <String>cmbCustomerId;
    public JFXTextField txtAddress;
    public JFXTextField txtPackSize;
    public JFXTextField txtDescription;
    public JFXComboBox <String>cmbItemId;
    public Label lblTotal;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtQty;
    public JFXTextField txtDiscount;
    public JFXButton btnPlaceOrder;
    public Label lblOrderId;

    public TableColumn colItemId;
    public TableColumn colDescription;
    public TableColumn colPackSIze;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colDiscount;
    public TableColumn colTotal;
    private String orderId;
    private final PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.PURCHASE_ORDER);

    public void initialize(){
        colItemId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSIze.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        TableColumn<OrderDetailTM, Button> lastCol = (TableColumn<OrderDetailTM, Button>) tblPayment.getColumns().get(7);
        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");

            btnDelete.setOnAction(event -> {
                tblPayment.getItems().remove(param.getValue());
                tblPayment.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisablePlaceOrderButton();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });
        loadCustomerIds();
        loadItemIds();
        lblOrderId.setText(generateNewOderId());
        btnPlaceOrder.setDisable(true);
        txtTitle.setFocusTraversable(false);
        txtTitle.setEditable(false);
        txtName.setFocusTraversable(false);
        txtName.setEditable(false);
        txtAddress.setFocusTraversable(false);
        txtAddress.setEditable(false);
        txtDescription.setFocusTraversable(false);
        txtDescription.setEditable(false);
        txtPackSize.setFocusTraversable(false);
        txtPackSize.setEditable(false);
        txtUnitPrice.setFocusTraversable(false);
        txtUnitPrice.setEditable(false);
        txtQtyOnHand.setFocusTraversable(false);
        txtQtyOnHand.setEditable(false);
        txtQty.setOnAction(event -> btnAddToCart.fire());
        txtQty.setEditable(false);
        txtDiscount.setEditable(false);
        btnAddToCart.setDisable(true);
        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisablePlaceOrderButton();
            if (newValue != null) {
                try {
                    try {
                        if (!existCustomer(newValue + "")) {

                            new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + newValue + "").show();
                        }
                        CustomerDTO customerDTO = purchaseOrderBO.searchCustomer(newValue+"");
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
        cmbItemId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemCode) -> {
            txtQty.setEditable(newItemCode != null);
            txtDiscount.setEditable(newItemCode != null);
            btnAddToCart.setDisable(newItemCode == null);
            if (newItemCode != null) {
                try {
                    if (!existItem(newItemCode + "")) {
                        //throw new NotFoundException("There is no such item associated with the id " + code);
                    }
                    /*Find Item*/
                    ItemDTO item = purchaseOrderBO.searchItem(newItemCode + "");
                    txtDescription.setText(item.getDescription());
                    txtPackSize.setText(item.getPackSize());
                    txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
                    Optional<OrderDetailTM> optOrderDetail = tblPayment.getItems().stream().filter(detail -> detail.getCode().equals(newItemCode)).findFirst();
                    txtQtyOnHand.setText((optOrderDetail.isPresent() ? item.getQtyOnHand() - optOrderDetail.get().getQty() : item.getQtyOnHand()) + "");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                txtDescription.clear();
                txtPackSize.clear();
                txtQty.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
                txtDiscount.clear();
            }
        });
        tblPayment.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {
            if (selectedOrderDetail != null) {
                cmbItemId.setDisable(true);
                cmbItemId.setValue(selectedOrderDetail.getCode());
                btnAddToCart.setText("Update");
                txtQtyOnHand.setText(Integer.parseInt(txtQtyOnHand.getText()) + selectedOrderDetail.getQty() + "");
                txtQty.setText(selectedOrderDetail.getQty() + "");
            } else {
                btnAddToCart.setText("Add To Cart");
                cmbItemId.setDisable(false);
                cmbItemId.getSelectionModel().clearSelection();
                txtQty.clear();
            }
        });
    }

    private void loadItemIds() {
        try {
            ArrayList<ItemDTO> all = purchaseOrderBO.getAllItems();
            for (ItemDTO dto : all) {
                cmbItemId.getItems().add(dto.getCode());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadCustomerIds() {
        try {
            ArrayList<CustomerDTO> all = purchaseOrderBO.getAllCustomer();
            for (CustomerDTO customerDTO : all) {
                cmbCustomerId.getItems().add(customerDTO.getId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void enableOrDisablePlaceOrderButton() {
        btnPlaceOrder.setDisable(!(cmbCustomerId.getSelectionModel().getSelectedItem() != null && !tblPayment.getItems().isEmpty()));
    }
    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return purchaseOrderBO.checkItemIsAvailable(code);
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return purchaseOrderBO.checkCustomerIsAvailable(id);
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        boolean b = saveOrder(lblOrderId.getText(), cmbCustomerId.getValue(), LocalDate.now(), LocalTime.now(), Double.valueOf(lblTotal.getText()),
                tblPayment.getItems().stream().map(tm -> new OrderDetailDTO(orderId, tm.getCode(), tm.getQty(),tm.getTotal(),tm.getDiscount())).collect(Collectors.toList()));

        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

        orderId = generateNewOderId();
        lblOrderId.setText(orderId);
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemId.getSelectionModel().clearSelection();
        tblPayment.getItems().clear();
        txtQty.clear();
        txtDiscount.clear();
        calculateTotal();
    }



    public boolean saveOrder(String orderId,String customerId, LocalDate orderDate,LocalTime orderTime,Double cost , List<OrderDetailDTO> orderDetails) {

        try {
            return purchaseOrderBO.purchaseOrder(new OrderDTO(orderId,customerId, orderDate, orderTime,cost, orderDetails));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        String itemCode = cmbItemId.getSelectionModel().getSelectedItem();
        String description = txtDescription.getText();
        String packSize = txtPackSize.getText();
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int customerQty = Integer.parseInt(txtQty.getText());
        double total = customerQty * unitPrice;
        double discount = (total / 100) * Double.parseDouble(txtDiscount.getText());
        double finalTotal = total - discount;


        boolean exists = tblPayment.getItems().stream().anyMatch(detail -> detail.getCode().equals(itemCode));

        if (exists) {
            OrderDetailTM orderDetailTM = tblPayment.getItems().stream().filter(detail -> detail.getCode().equals(itemCode)).findFirst().get();

            if (btnAddToCart.getText().equalsIgnoreCase("Update")) {
                orderDetailTM.setQty(customerQty);
                orderDetailTM.setDiscount(discount);
                orderDetailTM.setTotal(finalTotal);

               tblPayment.getSelectionModel().clearSelection();
            }
            tblPayment.refresh();
        } else {
            tblPayment.getItems().add(new OrderDetailTM(itemCode, description, packSize, customerQty, unitPrice, discount, finalTotal));
        }
        cmbItemId.getSelectionModel().clearSelection();
        cmbItemId.requestFocus();
        calculateTotal();
        enableOrDisablePlaceOrderButton();
    }

    private void calculateTotal() {
        double total = 0;
        for (OrderDetailTM detail : tblPayment.getItems()) {
            total += detail.getTotal();
        }
        lblTotal.setText(total+"");
    }

    private String generateNewOderId() {
        try {
            return purchaseOrderBO.generateNewOrderId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
