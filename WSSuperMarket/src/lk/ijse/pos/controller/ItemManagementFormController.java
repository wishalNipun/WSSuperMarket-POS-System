package lk.ijse.pos.controller;

import lk.ijse.pos.bo.BOFactory;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.pos.bo.custom.ItemBO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.view.tm.ItemTM;

import java.sql.*;
import java.util.ArrayList;

public class ItemManagementFormController {

    public JFXTextField txtUnitPrice;
    public JFXButton btnSave;
    public JFXTextField txtCode;
    public JFXTextField txtPackSize;
    public JFXTextField txtDescription;
    public TableView<ItemTM> tblItem;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colPackSize;
    public  TableColumn colUnitPrice;
    public TableColumn colQtyOnHand;
    public JFXButton btnDelete;
    public JFXTextField txtQtyOnHand;
    public JFXButton btnNew;
    private final ItemBO itemBO = (ItemBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.ITEM);
    public void initialize(){

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        textClearAndBtnDisable();
        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtCode.setText(newValue.getCode());
                txtDescription.setText(newValue.getDescription());
                txtPackSize.setText(newValue.getPackSize());
                txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
                txtQtyOnHand.setText(newValue.getQtyOnHand() + "");

                txtCode.setDisable(false);
                txtDescription.setDisable(false);
                txtPackSize.setDisable(false);
                txtUnitPrice.setDisable(false);
                txtQtyOnHand.setDisable(false);
            }
        });


        loadAllItems();
    }

    private void textClearAndBtnDisable() {
        txtCode.clear();
        txtDescription.clear();
        txtPackSize.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtCode.setDisable(true);
        txtDescription.setDisable(true);
        txtPackSize.setDisable(true);
        txtUnitPrice.setDisable(true);
        txtQtyOnHand.setDisable(true);
        txtCode.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        txtCode.setDisable(false);
        txtDescription.setDisable(false);
        txtUnitPrice.setDisable(false);
        txtPackSize.setDisable(false);
        txtQtyOnHand.setDisable(false);
        txtCode.clear();
        txtCode.setText(generateNewId());
        txtDescription.clear();
        txtPackSize.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtDescription.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblItem.getSelectionModel().clearSelection();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
            String code = txtCode.getText();
            String description = txtDescription.getText();
            String packSize = txtPackSize.getText();
            double unitPrice = Double.parseDouble(txtUnitPrice.getText());
            int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());


            if (btnSave.getText().equalsIgnoreCase("Save")) {
                try {
                    if (existItem(code)) {
                        new Alert(Alert.AlertType.ERROR, code + " already exists").show();
                    }
                    itemBO.ItemInsert(new ItemDTO(code,description,packSize,unitPrice,qtyOnHand));
                    tblItem.getItems().add(new ItemTM(code, description, packSize, unitPrice, qtyOnHand));

                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    if (!existItem(code)) {
                        new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
                    }
                    itemBO.ItemUpdate(new ItemDTO(code, description, packSize, unitPrice, qtyOnHand));

                    ItemTM selectedItem = tblItem.getSelectionModel().getSelectedItem();
                    selectedItem.setDescription(description);
                    selectedItem.setPackSize(packSize);
                    selectedItem.setQtyOnHand(qtyOnHand);
                    selectedItem.setUnitPrice(unitPrice);
                    tblItem.refresh();
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            btnNew.fire();
    }
    private void loadAllItems() {
        tblItem.getItems().clear();

        try {
            ArrayList<ItemDTO>  allItems = itemBO.getAllItems();
            for (ItemDTO item : allItems) {
                tblItem.getItems().add(new ItemTM(item.getCode(), item.getDescription(), item.getPackSize(),item.getUnitPrice(), item.getQtyOnHand()));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
      return itemBO.ItemExist(code);
    }
    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String code = tblItem.getSelectionModel().getSelectedItem().getCode();
        try {
            if (!existItem(code)) {
                new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
            }

            itemBO.ItemDelete(code);
            tblItem.getItems().remove(tblItem.getSelectionModel().getSelectedItem());
            tblItem.getSelectionModel().clearSelection();
           textClearAndBtnDisable();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + code).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private String generateNewId() {
        try {

            return itemBO.generateItemNewID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "I00-001";
    }
}
