package lk.ijse.pos.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class CashierDashBoardFormController {
    public AnchorPane loadContext;
    public Label lblDate;
    public Label lblTime;
    public void initialize(){
        try {
            loadDateAndTime();
            openContext();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void loadDateAndTime() {
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void openContext() throws IOException {
        setUI("CustomerManagementForm");

    }

    private void setUI(String URI) throws IOException {
        loadContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("/lk/ijse/pos/view/"+URI+".fxml"));
        loadContext.getChildren().add(parent);
    }

    public void btnCustomerManagementOnAction(ActionEvent actionEvent) throws IOException {
        setUI("CustomerManagementForm");
    }

    public void btnAddPaymentOnAction(ActionEvent actionEvent) throws IOException {
        setUI("PaymentForm");
    }

    public void btnOrderDetailsManagementOnAction(ActionEvent actionEvent) throws IOException {
        setUI("OrderDetailManagementForm");
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage1= (Stage)loadContext.getScene().getWindow();
        stage1.close();
        URL resource = getClass().getResource("/lk/ijse/pos/view/LoginForm.fxml");
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
