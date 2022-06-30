package lk.ijse.HostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
import lk.ijse.HostelManagementSystem.dto.ReservationDTO;
import lk.ijse.HostelManagementSystem.dto.RoomDTO;
import lk.ijse.HostelManagementSystem.dto.StudentDTO;
import lk.ijse.HostelManagementSystem.entity.Reservation;
import lk.ijse.HostelManagementSystem.entity.Room;
import lk.ijse.HostelManagementSystem.entity.Student;
import lk.ijse.HostelManagementSystem.entity.SuperEntity;
import lk.ijse.HostelManagementSystem.view.tm.CustomTM;
import lk.ijse.HostelManagementSystem.view.tm.ReservationTM;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public Label lblNonACRoom;
    public Label lblNonACFoodRoom;
    public Label lblACRoom;
    public Label lblACFoodRoom;

    private final ReservationBO reservationBO = (ReservationBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.RESERVATION);

    private String ResID;
    int beginNonACRoomCount =35;
    int beginNonACFoodRoomCount= 20;
    int beginACRoomCount =14;
    int beginACFoodRoomCount =10;


    public void initialize(){

        colRegID.setCellValueFactory(new PropertyValueFactory<>("res_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colRoomTypeID.setCellValueFactory(new PropertyValueFactory<>("room_type_id"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

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
           loadAllReservations();
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadDateAndTime();
        initialUI();
    }


    private void loadAllReservations() throws Exception {
        List<ReservationDTO> allReservations = reservationBO.getAllReservations();
        for (ReservationDTO dto :allReservations) {
            tblReservation.getItems().add(new ReservationTM(dto.getRes_id(),dto.getDate(),dto.getStudent().getStudent_id(),dto.getRoom().getRoom_type_id(),dto.getStatus()));
        }
    }

    private void loadAllRoomIds() throws Exception {
        List<RoomDTO> allRooms = reservationBO.getAllRooms();
        for (RoomDTO dto:allRooms) {
            cmbRoomTypeID.getItems().add(dto.getRoom_type_id());
        }
    }

    private void loadAllStudentIds() throws Exception {
        List<StudentDTO> allStudents = reservationBO.getAllStudents();
        for (StudentDTO dto:allStudents) {
            cmbStudentID.getItems().add(dto.getStudent_id());
        }
    }

    private void setRoomDetails(String selectedRoomId) {
        if (selectedRoomId != null) {
            try {
                if (!existRoom(selectedRoomId + "")) {
                    new Alert(Alert.AlertType.ERROR, "There is no such room associated with the id " + selectedRoomId + "").show();
                }

                List<RoomDTO> roomDTOList = reservationBO.searchRoom(selectedRoomId + "");

                for (RoomDTO dto:roomDTOList) {
                    txtRoomType.setText(dto.getType());
                    txtKeyMoney.setText(dto.getKey_money());
                    txtStatus.setEditable(false);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            txtRoomType.clear();
            txtKeyMoney.clear();
            txtPayment.clear();
            txtStatus.clear();
        }
    }

    private boolean existRoom(String id) throws Exception {
        return reservationBO.checkRoomIsAvailable(id);
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
        btnAddReservation.setDisable(!(cmbRoomTypeID.getSelectionModel().getSelectedItem() != null && cmbRoomTypeID.getSelectionModel().getSelectedItem() != null));
    }

    private void initialUI() {
        ResID = generateNewResId();
        lblRegID.setText("Res ID :" + ResID);
        btnAddReservation.setDisable(true);
        cmbStudentID.requestFocus();
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

/*
        ReservationDTO dto = new ReservationDTO();
        dto.setNonACRoomCount(beginNonACRoomCount);
        dto.setNonACFoodRoomCount(beginNonACFoodRoomCount);
        dto.setACRoomCount(beginACRoomCount);
        dto.setACFoodRoomCount(beginACFoodRoomCount);

        lblNonACRoomCount.setText(String.valueOf(dto.getNonACRoomCount()));
        lblNonACFoodRoomCount.setText(String.valueOf(dto.getNonACFoodRoomCount()));
        lblACRoomCount.setText(String.valueOf(dto.getACRoomCount()));
        lblACFoodRoomCount.setText(String.valueOf(dto.getACFoodRoomCount()));*/

    }

    private String generateNewResId() {
        return "R00-001";
    }

    public void txtSetStatusOnAction(ActionEvent actionEvent) {
        double payment = Double.parseDouble(txtPayment.getText());
        double keyMoney = Double.parseDouble(txtKeyMoney.getText());
        double status = keyMoney - payment;
        if (status == 0){
            txtStatus.setText("paid");
        }else {
            txtStatus.setText(status + " is payable");
        }
        btnAddReservation.setDisable(false);
    }

    public void btnAddReservationOnAction(ActionEvent actionEvent) {
        String studentId = cmbStudentID.getSelectionModel().getSelectedItem();
        String roomId = cmbRoomTypeID.getSelectionModel().getSelectedItem();
        String status = txtStatus.getText();

        try {
            List<StudentDTO> studentDTOList = reservationBO.searchStudent(studentId);
            for (StudentDTO dto :studentDTOList) {
                Student student = new Student(dto.getStudent_id(),dto.getName(),dto.getAddress(),dto.getContact_no(),dto.getDob(),dto.getGender());

                List<RoomDTO> roomDTOList = reservationBO.searchRoom(roomId);
                for (RoomDTO dto1 :roomDTOList) {
                    Room room = new Room(dto1.getRoom_type_id(),dto1.getType(),dto1.getKey_money(),dto1.getQty());

                    if (reservationBO.addReservation(new ReservationDTO(ResID,LocalDate.now(),student,room,status))){
                        Notifications notifications = Notifications.create().title("Add Reservation Successful !").text("Reservation has been added successfully...").hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
                        notifications.darkStyle();
                        notifications.show();

                       /* ReservationDTO reservationDTO = new ReservationDTO();

                        //-------------------update available room count--------------------------------

                        if (lblNonACRoom.getText().equals(roomId)) {
                            int i = reservationDTO.getNonACRoomCount();
                            int a = (i - 1);
                            reservationDTO.setNonACRoomCount(a);
                            lblNonACRoomCount.setText(String.valueOf(a));
                        } else if (lblNonACFoodRoom.getText().equals(roomId)) {
                            reservationDTO.setNonACFoodRoomCount(reservationDTO.getNonACFoodRoomCount()-1);
                            lblNonACFoodRoomCount.setText(String.valueOf(reservationDTO.getNonACFoodRoomCount()));
                        } else if (lblACRoom.getText().equals(roomId)) {
                            reservationDTO.setACRoomCount(reservationDTO.getACRoomCount()-1);
                            lblACRoomCount.setText(String.valueOf(reservationDTO.getACRoomCount()));
                        } else if (lblACFoodRoom.getText().equals(roomId)) {
                            reservationDTO.setACFoodRoomCount(reservationDTO.getACFoodRoomCount()-1);
                            lblACFoodRoomCount.setText(String.valueOf(reservationDTO.getACFoodRoomCount()));
                        }
                        System.out.println(reservationDTO.getNonACRoomCount());*/

                    }else {
                        new Alert(Alert.AlertType.ERROR, "Reservation has not been added successfully").show();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cancel();
    }


    private void cancel() {
        // ResID = generateNewOrderId();
        lblRegID.setText("Reg ID :" + ResID);
        cmbStudentID.getSelectionModel().clearSelection();
        cmbRoomTypeID.getSelectionModel().clearSelection();
        tblReservation.getItems().clear();
        txtPayment.clear();
        txtStatus.clear();
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
       cancel();
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
