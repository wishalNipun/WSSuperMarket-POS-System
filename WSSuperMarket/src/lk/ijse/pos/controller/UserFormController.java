package lk.ijse.pos.controller;

import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.custom.UserBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.pos.dto.UserDTO;
import lk.ijse.pos.view.tm.UserTM;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserFormController {
    public JFXTextField txtUserId;
    public JFXTextField txtName;
    public TableView<UserTM> tblUser;
    public TableColumn colUserId;
    public TableColumn colName;
    public TableColumn colRole;
    public TableColumn colTelNo;
    public TableColumn colEmail;
    public TableColumn colUserName;
    public TableColumn colPassword;
    public JFXTextField txtTelNo;
    public JFXButton btnSave;
    public JFXTextField txtEmail;
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public JFXButton btnNew;
    public JFXComboBox cmbRole;
    public JFXButton btnDelete;
    private final UserBO userBO = (UserBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.USER);
    public void initialize(){

        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colTelNo.setCellValueFactory(new PropertyValueFactory<>("telNo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        setRoles();

        textClearAndBtnDisable();
        loadAllCustomers();
        tblUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtUserId.setText(newValue.getUserId());
                txtUserName.setText(newValue.getUserName());
                txtName.setText(newValue.getName());
                cmbRole.setValue(newValue.getRole());
                txtTelNo.setText(newValue.getTelNo());
                txtEmail.setText(newValue.getEmail());
                txtUserName.setText(newValue.getUserName());
                txtPassword.setText(newValue.getPassword());

                txtUserId.setDisable(false);
                txtName.setDisable(false);
                cmbRole.setDisable(false);
                txtTelNo.setDisable(false);
                txtEmail.setDisable(false);
                txtUserName.setDisable(false);
                txtPassword.setDisable(false);
            }
        });

    }

    private void loadAllCustomers() {
        tblUser.getItems().clear();

        try {

            ArrayList<UserDTO> allUsers = userBO.getAllUsers();

            for (UserDTO user : allUsers) {
                tblUser.getItems().add(new UserTM(user.getUserId(),user.getName(),user.getRole(),user.getTelNo(),user.getEmail(),user.getUserName(),user.getPassword()));
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void textClearAndBtnDisable() {
        txtUserId.clear();
        txtName.clear();
        cmbRole.getSelectionModel().clearSelection();
        txtTelNo.clear();
        txtEmail.clear();
        txtUserName.clear();
        txtPassword.clear();

        txtUserId.setDisable(true);
        txtName.setDisable(true);
        cmbRole.setDisable(true);
        txtTelNo.setDisable(true);
        txtEmail.setDisable(true);
        txtUserName.setDisable(true);
        txtPassword.setDisable(true);

        txtUserId.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void setRoles() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.add("Administrator");
        obList.add("Cashier");
        cmbRole.setItems(obList);
    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        txtUserId.setDisable(false);
        txtName.setDisable(false);
        cmbRole.setDisable(false);
        txtTelNo.setDisable(false);
        txtEmail.setDisable(false);
        txtUserName.setDisable(false);
        txtPassword.setDisable(false);
        txtUserId.clear();
        txtUserId.setText(generateNewId());
        txtName.clear();
        cmbRole.getSelectionModel().clearSelection();
        txtTelNo.clear();
        txtEmail.clear();
        txtUserName.clear();
        txtPassword.clear();

        txtName.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblUser.getSelectionModel().clearSelection();
    }

    private String generateNewId() {
        try {

            return userBO.generateUserNewID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "U00-001";
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String id = txtUserId.getText();
        String name = txtName.getText();
        String role = String.valueOf(cmbRole.getValue());
        String telNo = txtTelNo.getText();
        String email = txtEmail.getText();
        String userName = txtUserName.getText();
        String password = txtPassword.getText();

        if (btnSave.getText().equalsIgnoreCase("save")) {
            /*Save Customer*/
            try {
                if (existUser(id)) {
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                }

                userBO.insertUser(new UserDTO(id,name,role,telNo,email,userName,password));

                tblUser.getItems().add(new UserTM(id,name,role,telNo,email,userName,password));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        } else {

            try {
                if (!existUser(id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
                }

                userBO.updateUser(new UserDTO(id,name,role,telNo,email,userName,password));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + id + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            UserTM selectedUser = tblUser.getSelectionModel().getSelectedItem();
            selectedUser.setName(name);
            selectedUser.setRole(role);
            selectedUser.setTelNo(telNo);
            selectedUser.setEmail(email);
            selectedUser.setUserName(userName);
            selectedUser.setPassword(password);
            tblUser.refresh();
        }

        btnNew.fire();
    }

    private boolean existUser(String id) throws SQLException, ClassNotFoundException {
        return userBO.existUser(id);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String id = tblUser.getSelectionModel().getSelectedItem().getUserId();
        try {
            if (!existUser(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + id).show();
            }

            userBO.deleteUser(id);
            tblUser.getItems().remove(tblUser.getSelectionModel().getSelectedItem());
            tblUser.getSelectionModel().clearSelection();
            textClearAndBtnDisable();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
