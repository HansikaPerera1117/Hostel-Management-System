package lk.ijse.HostelManagementSystem.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.HostelManagementSystem.business.BOFactory;
import lk.ijse.HostelManagementSystem.business.custom.ReservationBO;
import lk.ijse.HostelManagementSystem.dto.ReservationDTO;
import lk.ijse.HostelManagementSystem.view.tm.CustomTM;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class RemainKeyMoneyStudentFormController {
    public AnchorPane remainingKeyMoneyContext;
    public TableView<CustomTM> tblRemainingKeyMoney;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colContactNO;
    public TableColumn colRegNo;
    public TableColumn colRoomTypeID;
    public TableColumn colDate;
    public TableColumn colRemainingMoney;
    public Label lblDate;
    public Label lblTime;

    private final ReservationBO reservationBO = (ReservationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVATION);


    public void initialize(){

        colId.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContactNO.setCellValueFactory(new PropertyValueFactory<>("contact_no"));
        colRegNo.setCellValueFactory(new PropertyValueFactory<>("res_id"));
        colRoomTypeID.setCellValueFactory(new PropertyValueFactory<>("room_type_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colRemainingMoney.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadDateAndTime();

        try {
            loadAllRemainKeyMonetStudents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAllRemainKeyMonetStudents() throws Exception {
        List<ReservationDTO> reservationDTOS = reservationBO.remainKeyMoneyStudents();
        for (ReservationDTO reservationDTO : reservationDTOS) {
            tblRemainingKeyMoney.getItems().add(new CustomTM(reservationDTO.getStudent().getStudent_id(),reservationDTO.getStudent().getName(),reservationDTO.getStudent().getContact_no(),reservationDTO.getRes_id(),reservationDTO.getRoom().getRoom_type_id(),reservationDTO.getDate(),reservationDTO.getStatus()));
        }

    }

    public void backToDashBoardOnAction(MouseEvent event) throws IOException {
        setUI(remainingKeyMoneyContext,"DashBoardForm");
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
