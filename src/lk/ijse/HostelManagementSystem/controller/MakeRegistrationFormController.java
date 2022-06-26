package lk.ijse.HostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.HostelManagementSystem.view.tm.ReservationTM;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class MakeRegistrationFormController {
    public AnchorPane makeRegistrationContext;
    public JFXTextField txtStudentName;
    public Label lblDate;
    public Label lblTime;
    public Label lblNonACRoomCount;
    public Label lblNonACFoodRoomCount;
    public Label lblACRoomCount;
    public Label lblACFoodRoomCount;
    public Label lblRegID;
    public JFXComboBox<String> cmbStudentID;
    public JFXComboBox<String> cmbRoomTypeID;
    public JFXTextField txtKeyMoney;
    public JFXTextField txtRoomType;
    public JFXTextField txtPayment;
    public JFXTextField txtStatus;
    public TableView<ReservationTM> tblReservation;
    public TableColumn colRegID;
    public TableColumn colDate;
    public TableColumn colStudentID;
    public TableColumn colRoomTypeID;
    public TableColumn colStatus;
    public JFXButton btnAddReservation;
    public JFXButton btnCancel;


    public void initialize(){
        loadDateAndTime();
    }

    public void btnAddNewStudentOnAction(ActionEvent actionEvent) throws IOException {
        setUI(makeRegistrationContext,"studentForm");
    }

    public void backToDashBoardOnAction(MouseEvent event) throws IOException {
        setUI(makeRegistrationContext,"DashBoardForm");
    }

    public void setUI(AnchorPane ap, String location) throws IOException {
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/HostelManagementSystem/view/"+location+".fxml"))));
        stage.setTitle(location);
        stage.centerOnScreen();
    }

    public void btnAddReservationOnAction(ActionEvent actionEvent) {
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
    }

    private void loadDateAndTime() {
        /* set date*/
        lblDate.setText("Date :"+new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        /*set time*/
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText("Time :"+currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }), new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

}
