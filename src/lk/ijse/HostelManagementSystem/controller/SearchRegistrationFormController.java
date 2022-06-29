package lk.ijse.HostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.HostelManagementSystem.business.BOFactory;
import lk.ijse.HostelManagementSystem.business.SuperBO;
import lk.ijse.HostelManagementSystem.business.custom.SearchRegistrationBO;
import lk.ijse.HostelManagementSystem.dto.ReservationDTO;
import lk.ijse.HostelManagementSystem.dto.RoomDTO;
import lk.ijse.HostelManagementSystem.dto.StudentDTO;
import lk.ijse.HostelManagementSystem.view.tm.ReservationTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class SearchRegistrationFormController {
    public AnchorPane searchRegistrationContext;
    public JFXComboBox<String>cmbRegID;
    public JFXComboBox<String> cmbStudentID;
    public JFXComboBox<String> cmbRoomID;
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
    public JFXTextField txtStudentName;
    public JFXTextField txtKeyMoney;
    public JFXTextField txtRoomType;
    public JFXTextField txtStatus;
    public JFXTextField txtRoomTypeID;
    public Label lblDate;
    public Label lblTime;


    private String selectedRegId=null;

    private final SearchRegistrationBO searchRegistrationBO = (SearchRegistrationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SEARCHREGISTRATION);


    public void initialize(){

        colRegID.setCellValueFactory(new PropertyValueFactory<>("res_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colRoomTypeID.setCellValueFactory(new PropertyValueFactory<>("room_type_id"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        cmbRegID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedRegId=newValue;
            enableOrDisableRemoveReservationButton();
            setRegDetailsToTable(newValue);
            lblRegID.setText("Reg_ID : "+newValue);

        });

        cmbStudentID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cmbRegID.getItems().clear();
            setRegIdsAccordingToStudentId(newValue);
            lblRegID.setText("Reg_ID : ");
            lblStudentID.setText("Student ID :"+newValue);
            lblRegDate.setText("Reg date :");
            //cmbRoomID.getSelectionModel().clearSelection();
        });

        cmbRoomID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            cmbRegID.getItems().clear();
            setRegIdsAccordingToRoomTypeId(newValue);
            lblRegID.setText("Reg_ID : ");
            lblStudentID.setText("Student ID :");
            lblRegDate.setText("Reg date :");
            //cmbStudentID.getSelectionModel().clearSelection();
        });

        tblSearchReservation.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedReservationDetail) -> {
            changeReservationDetails(selectedReservationDetail);
        });

        loadDateAndTime();

        try {
            loadAllResIds();
            loadAllStudentIds();
            loadAllRoomIds();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void setRegIdsAccordingToRoomTypeId(String selectedRoomId) {
        try {
            List<ReservationDTO> allReservationsAccordingToRoom = searchRegistrationBO.getAllReservationsAccordingToRoom(selectedRoomId);
            for (ReservationDTO dto:allReservationsAccordingToRoom) {
                cmbRegID.getItems().add(dto.getRes_id());
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load reservation ids").show();
            e.printStackTrace();
        }
    }

    private void changeReservationDetails(ReservationTM selectedReservationDetail) {

    }

    private void setRegIdsAccordingToStudentId(String selectedStudentId) {
        try {
            List<ReservationDTO> allReservationsAccordingToStudent = searchRegistrationBO.getAllReservationsAccordingToStudent(selectedStudentId);
            for (ReservationDTO dto:allReservationsAccordingToStudent) {
                cmbRegID.getItems().add(dto.getRes_id());
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load reservation ids").show();
            e.printStackTrace();
        }
    }

    private void setRegDetailsToTable(String selectedResId) {
        tblSearchReservation.getItems().clear();

        if (selectedResId != null) {
                try {
                    if (!existReservation(selectedResId + "")) {
                        new Alert(Alert.AlertType.ERROR, "There is no such reservation associated with the id " + selectedResId + "").show();
                        btnRemoveReservation.setDisable(true);
                    }
                    btnRemoveReservation.setDisable(false);
                    List<ReservationDTO> reservationDTOS = searchRegistrationBO.searchReservationDetails(selectedResId + "");
                    for (ReservationDTO dto : reservationDTOS) {

                        lblStudentID.setText("Student ID :"+dto.getStudent().getStudent_id());
                        lblRegDate.setText("Reg date :"+dto.getDate());

                        String res_id = dto.getRes_id();
                        LocalDate date = dto.getDate();
                        String student_id = dto.getStudent().getStudent_id();
                        String room_type_id = dto.getRoom().getRoom_type_id();
                        String status = dto.getStatus();

                        tblSearchReservation.getItems().add(new ReservationTM(res_id,date,student_id,room_type_id,status));
                    }

                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Failed to find the reservation " + selectedResId + "" + e).show();
                }

        } else {
            tblSearchReservation.getItems().clear();
        }
    }

    private boolean existReservation(String id) throws Exception {
       return searchRegistrationBO.reservationExist(id);
    }

    private void enableOrDisableRemoveReservationButton() {
        btnRemoveReservation.setDisable(!(cmbRegID.getSelectionModel().getSelectedItem() != null && !tblSearchReservation.getItems().isEmpty()));
    }

    private void loadAllStudentIds() throws Exception {
        List<StudentDTO> allStudents = searchRegistrationBO.getAllStudents();
        for (StudentDTO dto:allStudents) {
            cmbStudentID.getItems().add(dto.getStudent_id());
        }
    }

    private void loadAllRoomIds() throws Exception {
        List<RoomDTO> allRooms = searchRegistrationBO.getAllRooms();
        for (RoomDTO dto : allRooms) {
            cmbRoomID.getItems().add(dto.getRoom_type_id());
        }

    }

    private void loadAllResIds() throws Exception {
        List<ReservationDTO> allReservations = searchRegistrationBO.getAllReservations();
        for (ReservationDTO dto:allReservations) {
            cmbRegID.getItems().add(dto.getRes_id());
        }
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
