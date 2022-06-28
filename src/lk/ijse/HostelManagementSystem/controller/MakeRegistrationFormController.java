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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.HostelManagementSystem.business.BOFactory;
import lk.ijse.HostelManagementSystem.business.SuperBO;
import lk.ijse.HostelManagementSystem.business.custom.ReservationBO;
import lk.ijse.HostelManagementSystem.business.custom.RoomBO;
import lk.ijse.HostelManagementSystem.business.custom.StudentBO;
import lk.ijse.HostelManagementSystem.dto.StudentDTO;
import lk.ijse.HostelManagementSystem.view.tm.ReservationTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

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
    public JFXButton btnAddNewStudent;

    private final ReservationBO reservationBO = (ReservationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVATION);


    private String ResID;

    public void initialize(){

        cmbStudentID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisableAddReservationButton();
            setStudentDetails(newValue);
        });

        cmbRoomTypeID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newRoomId) -> {
            txtPayment.setEditable(newRoomId != null);
            txtStatus.setEditable(newRoomId != null);
            setRoomDetails(newRoomId);
        });

        try {
            loadAllStudentIds();
            loadAllRoomIds();
        } catch (Exception e) {
            e.printStackTrace();
        }


        loadDateAndTime();
        initialUI();
    }

    private void loadAllRoomIds() {

    }

    private void loadAllStudentIds() throws Exception {
        List<StudentDTO> allStudents = reservationBO.getAllStudents();
        for (StudentDTO dto:allStudents) {
            cmbStudentID.getItems().add(dto.getStudent_id());
        }

    }

    private void setRoomDetails(String selectedRoomId) {

    }

    private void setStudentDetails(String selectedStudentId) {
        if (selectedStudentId != null) {
                try {
                    if (!existStudent(selectedStudentId + "")) {
                        new Alert(Alert.AlertType.ERROR, "There is no such student associated with the id " + selectedStudentId + "").show();
                    }else {
                        List<StudentDTO> studentDTOList = reservationBO.searchStudent(selectedStudentId + "");
                        for (StudentDTO dto : studentDTOList) {
                            txtStudentName.setText(dto.getName());
                        }
                    }
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Failed to find the student " + selectedStudentId + "" + e).show();
                    e.printStackTrace();
                }
        } else {
            txtStudentName.clear();
        }
    }

    private boolean existStudent(String id) throws Exception {
        return reservationBO.checkStudentIsAvailable(id);
    }

    private void enableOrDisableAddReservationButton() {

    }

    private void initialUI() {
        ResID = generateNewResId();
        lblRegID.setText("Res ID :" + ResID);
        btnAddReservation.setDisable(true);
        txtStudentName.setFocusTraversable(false);
        txtStudentName.setEditable(false);
        txtRoomType.setFocusTraversable(false);
        txtRoomType.setEditable(false);
        txtKeyMoney.setFocusTraversable(false);
        txtKeyMoney.setEditable(false);
        txtPayment.setFocusTraversable(false);
        txtPayment.setEditable(false);
        txtStatus.setFocusTraversable(false);
        txtStatus.setEditable(false);
        txtStatus.setOnAction(event -> btnAddReservation.fire());
    }

    private String generateNewResId() {
        return null;
    }


    public void btnAddReservationOnAction(ActionEvent actionEvent) {
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
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
