package lk.ijse.HostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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
import lk.ijse.HostelManagementSystem.entity.Room;
import lk.ijse.HostelManagementSystem.entity.Student;
import lk.ijse.HostelManagementSystem.view.tm.ReservationTM;
import org.controlsfx.control.Notifications;

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


    private String selectedRegId = null;
    private String updateStatus = null;


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
            try {
                changeReservationDetails(selectedReservationDetail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        loadDateAndTime();
        initialUI();

        try {
            loadAllResIds();
            loadAllStudentIds();
            loadAllRoomIds();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialUI() {
        btnConfirmEdits.setDisable(true);
        btnRemoveReservation.setDisable(true);
        btnUpdate.setDisable(true);
        txtStudentName.setFocusTraversable(false);
        txtStudentName.setEditable(false);
        txtRoomTypeID.setFocusTraversable(false);
        txtRoomTypeID.setEditable(false);
        txtRoomType.setFocusTraversable(false);
        txtRoomType.setEditable(false);
        txtKeyMoney.setFocusTraversable(false);
        txtKeyMoney.setEditable(false);
        txtStatus.setOnAction(event -> btnUpdate.fire());
        txtStatus.setEditable(false);
    }

    private void changeReservationDetails(ReservationTM selectedReservationDetail) throws Exception {
        if (selectedReservationDetail != null) {
            cmbRegID.setDisable(true);
            cmbStudentID.setDisable(true);
            cmbRoomID.setDisable(true);
            List<StudentDTO> studentDTOList = searchRegistrationBO.searchStudentDetails(selectedReservationDetail.getStudent_id());
            for (StudentDTO dto : studentDTOList) {
                txtStudentName.setText(dto.getName());
            }
            txtRoomTypeID.setText(selectedReservationDetail.getRoom_type_id());
            List<RoomDTO> roomDTOList = searchRegistrationBO.searchRoomDetails(selectedReservationDetail.getRoom_type_id());
            for (RoomDTO dto : roomDTOList) {
                txtRoomType.setText(dto.getType());
                txtKeyMoney.setText(dto.getKey_money());
            }
            txtStatus.setText(selectedReservationDetail.getStatus());
            txtStatus.setEditable(true);
            btnUpdate.setDisable(false);
        } else {
            cmbRegID.setDisable(false);
            cmbStudentID.setDisable(false);
            cmbRoomID.setDisable(false);
            cmbRegID.getSelectionModel().clearSelection();
            cmbRoomID.getSelectionModel().clearSelection();
            cmbStudentID.getSelectionModel().clearSelection();
            txtStudentName.clear();
            txtRoomTypeID.clear();
            txtRoomType.clear();
            txtKeyMoney.clear();
            txtStatus.clear();
        }
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
        String roomID = txtRoomTypeID.getText();
        updateStatus = txtStatus.getText();

        boolean exists = tblSearchReservation.getItems().stream().anyMatch(detail -> detail.getRoom_type_id().equals(roomID));

        if (exists) {
            if (tblSearchReservation.getSelectionModel().getSelectedItem() != null) {
                ReservationTM selectedItem = tblSearchReservation.getSelectionModel().getSelectedItem();
                selectedItem.setStatus(updateStatus);
                tblSearchReservation.refresh();
                btnConfirmEdits.setDisable(false);

                Notifications notifications = Notifications.create().title("Successful !").text("Reservation status has been updated successfully...").hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
                notifications.darkStyle();
                notifications.show();
            }
            tblSearchReservation.refresh();
        } else{
            new Alert(Alert.AlertType.ERROR, "Room not exists").show();
        }

        txtStudentName.clear();
        txtRoomTypeID.clear();
        txtRoomType.clear();;
        txtKeyMoney.clear();
        txtStatus.clear();
    }

    public void btnConfirmEditsOnAction(ActionEvent actionEvent) {
        try {
            if (!existReservation(selectedRegId)) {
                new Alert(Alert.AlertType.ERROR, "There is no such reservation associated with the id " + selectedRegId ).show();
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.get().equals(ButtonType.YES)) {

                ObservableList<ReservationTM> items = tblSearchReservation.getItems();
                boolean b= false;
                for (ReservationTM reservationTM: items) {
                    List<StudentDTO> studentDTOList = searchRegistrationBO.searchStudentDetails(reservationTM.getStudent_id());
                    for (StudentDTO dto : studentDTOList) {
                        Student student = new Student(dto.getStudent_id(), dto.getName(), dto.getAddress(), dto.getContact_no(), dto.getDob(), dto.getGender());

                        List<RoomDTO> roomDTOList = searchRegistrationBO.searchRoomDetails(reservationTM.getRoom_type_id());
                        for (RoomDTO dto1 : roomDTOList) {
                            Room room = new Room(dto1.getRoom_type_id(), dto1.getType(), dto1.getKey_money(), dto1.getQty());

                            if(searchRegistrationBO.updateReservation(new ReservationDTO(selectedRegId, reservationTM.getDate(), student, room, updateStatus))){
                                new Alert(Alert.AlertType.INFORMATION, "Reservation details has been updated successfully").show();
                            }

                        }
                    }
                }

            }else {
                cancelSearching();
            }

        } catch (Exception e ) {
            e.printStackTrace();
        }
        btnConfirmEdits.setDisable(true);
    }

    private void cancelSearching() throws Exception {
        btnUpdate.setDisable(true);
        btnConfirmEdits.setDisable(true);
        btnRemoveReservation.setDisable(true);
        cmbRegID.setDisable(false);
        cmbStudentID.setDisable(false);
        cmbRoomID.setDisable(false);
        cmbRegID.getSelectionModel().clearSelection();
        cmbStudentID.getSelectionModel().clearSelection();
        cmbRoomID.getSelectionModel().clearSelection();
        lblRegID.setText("Reg_ID : ");
        lblRegDate.setText("Reg date :");
        lblStudentID.setText("Student ID :");
        txtStudentName.clear();
        txtRoomTypeID.clear();
        txtRoomType.clear();;
        txtKeyMoney.clear();
        txtStatus.clear();
        tblSearchReservation.getItems().clear();
        loadAllResIds();
    }

    public void btnCancelOnAction(ActionEvent actionEvent) throws Exception {
        cancelSearching();
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
