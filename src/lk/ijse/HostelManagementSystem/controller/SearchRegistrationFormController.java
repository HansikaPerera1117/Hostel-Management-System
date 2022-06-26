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

public class SearchRegistrationFormController {
    public AnchorPane searchRegistrationContext;
    public JFXComboBox<String>cmbRegID;
    public JFXComboBox<String> cmbStudentID;
    public JFXButton btnUpdate;
    public JFXButton btnConfirmEdits;
    public JFXButton btnCancel;
    public JFXButton btnRemoveReservation;
    public Label lblStudentID;
    public Label lblRegID;
    public Label lblRegDate;
    public TableView<ReservationTM> tblSearchReservation;
    public TableColumn colRegID;
    public TableColumn colDate;
    public TableColumn colStudentID;
    public TableColumn colRoomTypeID;
    public TableColumn colStatus;
    public TableColumn colDelete;
    public JFXTextField txtStudentName;
    public JFXTextField txtKeyMoney;
    public JFXTextField txtRoomType;
    public JFXTextField txtStatus;
    public JFXTextField txtRoomTypeID;
    public Label lblDate;
    public Label lblTime;

   public void initialize(){
       loadDateAndTime();
   }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnConfirmEditsOnAction(ActionEvent actionEvent) {
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
    }

    public void btnRemoveReservationOnAction(ActionEvent actionEvent) {
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
    }

    public void backToDashBoardOnAction(MouseEvent event) throws IOException {
        setUI(searchRegistrationContext,"DashBoardForm");
    }

    public void setUI(AnchorPane ap, String location) throws IOException {
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/HostelManagementSystem/view/"+location+".fxml"))));
        stage.setTitle(location);
        stage.centerOnScreen();
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
