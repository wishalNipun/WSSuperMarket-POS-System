package lk.ijse.pos.controller;

import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.custom.CustomerBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.view.tm.CustomerTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerManagementFormController {

    public JFXTextField txtCustomerId;
    public JFXTextField txtName;
    public JFXTextField txtTitle;
    public TableColumn colCustomerId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colTitle;
    public TableColumn colCity;
    public TableColumn colProvince;
    public TableColumn colPostalCode;
    public JFXTextField txtCity;
    public JFXButton btnSave;
    public JFXButton btnNew;
    public JFXTextField txtProvince;
    public JFXButton btnDelete;
    public JFXTextField txtAddress;
    public JFXTextField txtPostalCode;
    public TableView<CustomerTM> tblCustomer;
    private final CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    public void initialize() {

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        textClearAndBtnDisable();
        loadAllCustomers();
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtCustomerId.setText(newValue.getId());
                txtTitle.setText(newValue.getTitle());
                txtName.setText(newValue.getName());
                txtAddress.setText(newValue.getAddress());
                txtCity.setText(newValue.getCity());
                txtProvince.setText(newValue.getProvince());
                txtPostalCode.setText(newValue.getPostalCode()+"");

                txtCustomerId.setDisable(false);
                txtTitle.setDisable(false);
                txtName.setDisable(false);
                txtAddress.setDisable(false);
                txtCity.setDisable(false);
                txtProvince.setDisable(false);
                txtPostalCode.setDisable(false);
            }
        });

    }

    private void textClearAndBtnDisable() {
        txtCustomerId.clear();
        txtTitle.clear();
        txtName.clear();
        txtAddress.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();
        txtCustomerId.setDisable(true);
        txtTitle.setDisable(true);
        txtName.setDisable(true);
        txtAddress.setDisable(true);
        txtCity.setDisable(true);
        txtProvince.setDisable(true);
        txtPostalCode.setDisable(true);
        txtCustomerId.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void loadAllCustomers() {
        tblCustomer.getItems().clear();

        try {

            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers();

            for (CustomerDTO customer : allCustomers) {
                tblCustomer.getItems().add(new CustomerTM(customer.getId(), customer.getTitle(), customer.getName(), customer.getAddress(), customer.getCity(), customer.getProvince(), customer.getPostalCode()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtCustomerId.getText();
        String title = txtTitle.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String city = txtCity.getText();
        String province = txtProvince.getText();
        int postalCode = Integer.parseInt(txtPostalCode.getText());

        if (btnSave.getText().equalsIgnoreCase("save")) {
            /*Save Customer*/
            try {
                if (existCustomer(id)) {
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                }

               customerBO.insertCustomer(new CustomerDTO(id,title,name,address,city,province,postalCode));

                tblCustomer.getItems().add(new CustomerTM(id,title,name,address,city,province,postalCode));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        } else {

            try {
                if (!existCustomer(id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
                }

                customerBO.updateCustomer(new CustomerDTO(id,title,name,address,city,province,postalCode));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + id + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            CustomerTM selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
            selectedCustomer.setName(name);
            selectedCustomer.setAddress(address);
            selectedCustomer.setCity(city);
            selectedCustomer.setPostalCode(postalCode);
            selectedCustomer.setProvince(province);
            tblCustomer.refresh();
        }

        btnNew.fire();
    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        txtCustomerId.setDisable(false);
        txtTitle.setDisable(false);
        txtName.setDisable(false);
        txtAddress.setDisable(false);
        txtCity.setDisable(false);
        txtProvince.setDisable(false);
        txtPostalCode.setDisable(false);
        txtCustomerId.clear();
        txtCustomerId.setText(generateNewId());
        txtTitle.clear();
        txtName.clear();
        txtAddress.clear();
        txtCity.clear();
        txtProvince.clear();
        txtPostalCode.clear();
        txtTitle.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblCustomer.getSelectionModel().clearSelection();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String code = tblCustomer.getSelectionModel().getSelectedItem().getId();
        try {
            if (!existCustomer(code)) {
                new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
            }

            customerBO.deleteCustomer(code);
            tblCustomer.getItems().remove(tblCustomer.getSelectionModel().getSelectedItem());
            tblCustomer.getSelectionModel().clearSelection();
            textClearAndBtnDisable();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + code).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.customerExist(id);
    }
    private String generateNewId() {
        try {

            return customerBO.generateCustomerNewID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "C00-001";
    }
}
