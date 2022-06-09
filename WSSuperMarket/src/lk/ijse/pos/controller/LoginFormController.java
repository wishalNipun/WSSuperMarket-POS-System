package lk.ijse.pos.controller;

import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.custom.LogBO;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.pos.dto.UserDTO;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class LoginFormController {
    public JFXPasswordField txtPassword;
    public JFXTextField txtUserName;
    public Label lblWrong;
    public AnchorPane loginFormContext;
    private final LogBO logBO = (LogBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.LOG);

    public void btnLoginOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {

        if(txtUserName.getText().equals("") && txtPassword.getText().equals("")){
            lblWrong.setText("Please enter your UserName and Password");

        }else if (txtUserName.getText()!=null&& txtPassword.getText()!=null){
            UserDTO user = logBO.getUser(txtUserName.getText(), txtPassword.getText());

            if (user.getRole().equals("Administrator")){

                setUI("AdministratorDashBoardForm");
            }else {
                setUI("CashierDashBoardForm");
            }

        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again !").show();
        }
    }
    private void setUI(String URI) throws IOException {

        Stage stage1= (Stage)loginFormContext.getScene().getWindow();
        stage1.close();
        URL resource = getClass().getResource("/lk/ijse/pos/view/"+URI+".fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage stage2= new Stage();
        stage2.setTitle("WSSuperMarket POS System");
        Image image = new Image("/lk/ijse/pos/assets/images/iconSoftware.png");
        stage2.getIcons().add(image);
        stage2.setScene(scene);
        stage2.centerOnScreen();
        stage2.show();
    }
}
