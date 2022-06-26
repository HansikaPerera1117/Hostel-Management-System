package lk.ijse.HostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.HostelManagementSystem.view.tm.RoomTM;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class ManageRoomFormController {
    public AnchorPane roomsContext;
    public JFXTextField txtRoomTypeID;
    public JFXTextField txtKeyMoney;
    public JFXTextField txtRoomType;
    public JFXTextField txtQty;
    public TableView<RoomTM> tblRoom;
    public TableColumn colId;
    public TableColumn colType;
    public TableColumn colKeyMoney;
    public TableColumn colQty;
    public TableColumn colDelete;
    public JFXButton btnSave;
    public JFXButton btnAddNewRoom;
    public Label lblDate;
    public Label lblTime;


    public void initialize(){
        loadDateAndTime();
    }


    public void btnSaveRoomOnAction(ActionEvent actionEvent) {
    }

    public void btnNewRoomOnAction(ActionEvent actionEvent) {
    }

    public void backToDashBoardOnAction(MouseEvent event) throws IOException {
        setUI(roomsContext,"DashBoardForm");
    }

    public void setUI(AnchorPane ap, String location) throws IOException {
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/HostelManagementSystem/view/"+location+".fxml"))));
        stage.setTitle(location);
        stage.centerOnScreen();
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
