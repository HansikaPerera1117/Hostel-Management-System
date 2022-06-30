package lk.ijse.HostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.HostelManagementSystem.business.BOFactory;
import lk.ijse.HostelManagementSystem.business.custom.StudentBO;
import lk.ijse.HostelManagementSystem.dto.StudentDTO;
import lk.ijse.HostelManagementSystem.validation.ValidationUtil;
import lk.ijse.HostelManagementSystem.view.tm.StudentTM;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class StudentFormController {
    public AnchorPane studentContext;
    public JFXTextField txtStudentId;
    public JFXTextField txtStudentName;
    public JFXTextField txtAddress;
    public JFXTextField txtContactNo;
    public JFXDatePicker dtDOB;
    public JFXComboBox<String> cmbGender;
    public TableView<StudentTM> tblStudent;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContactNo;
    public TableColumn colDOB;
    public TableColumn colGender;
    public TableColumn colDelete;
    public JFXButton btnSave;
    public JFXButton btnAddNewStudent;
    public Label lblTime;
    public Label lblDate;

    private int attempts = 0;

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    private final StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STUDENT);


    public void initialize(){

        ObservableList<String> obList = FXCollections.observableArrayList();
        obList.add("Male");
        obList.add("Female");
        cmbGender.setItems(obList);

        colId.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contact_no"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null){
                txtStudentId.setText(newValue.getStudent_id());
                txtStudentId.setEditable(false);
                txtStudentName.setText(newValue.getName());
                txtAddress.setText(newValue.getAddress());
                txtContactNo.setText(newValue.getContact_no());
                dtDOB.setValue(newValue.getDob());
                cmbGender.setValue(newValue.getGender());

                txtStudentId.setDisable(false);
                txtStudentName.setDisable(false);
                txtAddress.setDisable(false);
                txtContactNo.setDisable(false);
                dtDOB.setDisable(false);
                cmbGender.setDisable(false);
            }
        });

        loadDateAndTime();
        initialUI();

        //----------------------------Validation--------------------------------------------
        Pattern idPattern = Pattern.compile("^(P/|G/)[1-9][0-9]{3,5}$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,25}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,40}$");
        Pattern contactNoPattern = Pattern.compile("^(011|070|071|072|074|075|076|077|078)[0-9]{7}$");

        map.put(txtStudentId,idPattern);
        map.put(txtStudentName,namePattern);
        map.put(txtAddress,addressPattern);
        map.put(txtContactNo,contactNoPattern);


        try {
            loadAllStudents();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initialUI() {
        txtStudentId.clear();
        txtStudentName.clear();
        txtAddress.clear();
        txtContactNo.clear();
        dtDOB.getEditor().clear();
        cmbGender.getSelectionModel().clearSelection();
        txtStudentId.setDisable(true);
        txtStudentName.setDisable(true);
        txtAddress.setDisable(true);
        txtContactNo.setDisable(true);
        dtDOB.setDisable(true);
        cmbGender.setDisable(true);
        btnSave.setDisable(true);
    }

    private void loadAllStudents() throws Exception {
        tblStudent.getItems().clear();

        List<StudentDTO> allStudents = studentBO.getAllStudents();
        for (StudentDTO dto : allStudents){
            Button btn = new Button("Delete");
          tblStudent.getItems().add( new StudentTM(dto.getStudent_id(),dto.getName(),dto.getAddress(),dto.getContact_no(),dto.getDob(),dto.getGender(),btn));

          btn.setOnAction(e->{

              //----------------------delete Student-------------------------------------
              String student_id = tblStudent.getSelectionModel().getSelectedItem().getStudent_id();

              try {
                  if (!existsStudent(student_id)){
                      new Alert(Alert.AlertType.ERROR, "There is no such student associated with the id " + student_id).show();
                  }

                  Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure?", ButtonType.YES, ButtonType.NO);
                  Optional<ButtonType> buttonType = alert.showAndWait();

                  if (buttonType.get().equals(ButtonType.YES)) {

                      studentBO.deleteStudent(student_id);
                      tblStudent.getItems().remove(tblStudent.getSelectionModel().getSelectedItem());
                      tblStudent.getSelectionModel().clearSelection();
                      initialUI();
                      attempts--;

                      Notifications notifications = Notifications.create().title("Successful !").text("Student has been deleted successfully...").hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
                      notifications.darkStyle();
                      notifications.show();

                  }

              } catch (Exception exception) {
                  new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + student_id).show();
                  exception.printStackTrace();
              }

          });
        }
    }

    public void btnNewStudentOnAction(ActionEvent actionEvent) {
        txtStudentId.setEditable(true);
        txtStudentId.setDisable(false);
        txtStudentName.setDisable(false);
        txtAddress.setDisable(false);
        txtContactNo.setDisable(false);
        dtDOB.setDisable(false);
        cmbGender.setDisable(false);
        txtStudentId.clear();
        txtStudentName.clear();
        txtAddress.clear();
        txtContactNo.clear();
        dtDOB.getEditor().clear();
        cmbGender.getSelectionModel().clearSelection();
        txtStudentId.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblStudent.getSelectionModel().clearSelection();
    }

    public void btnSaveStudentOnAction(ActionEvent actionEvent) {
       if (btnSave.getText().equalsIgnoreCase("Save")) {
           //-----------------------Save Student-----------------------------
           attempts++;
           if (attempts <= 125) {
               try {
                   if (existsStudent(txtStudentId.getText())) {
                       new Alert(Alert.AlertType.ERROR, txtStudentId.getText() + " already exists").show();
                   } else {
                       if (studentBO.addStudent(new StudentDTO(txtStudentId.getText(), txtStudentName.getText(), txtAddress.getText(), txtContactNo.getText(), dtDOB.getValue(), String.valueOf(cmbGender.getValue())))) {
                           Notifications notifications = Notifications.create().title("Successful !").text("Student has been saved successfully...").hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
                           notifications.darkStyle();
                           notifications.show();
                       }
                   }
               } catch (Exception e) {
                   new Alert(Alert.AlertType.ERROR, "Failed to save the student " + e.getMessage()).show();
                   e.printStackTrace();
               }
           }
       }else{
           //---------------Update Student-------------------------------------------
           try {
               if (!existsStudent(txtStudentId.getText())) {
                   new Alert(Alert.AlertType.ERROR, "There is no such student associated with the id " + txtStudentId.getText() ).show();
               }else {
                   if (studentBO.updateStudent(new StudentDTO(txtStudentId.getText(), txtStudentName.getText(), txtAddress.getText(), txtContactNo.getText(), dtDOB.getValue(), String.valueOf(cmbGender.getValue())))) {
                       Notifications notifications = Notifications.create().title("Successful !").text("Student has been updated successfully...").hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
                       notifications.darkStyle();
                       notifications.show();
                   }
               }

           } catch (Exception e) {
               new Alert(Alert.AlertType.ERROR, "Failed to update the student " + e.getMessage()).show();
               e.printStackTrace();
           }
       }
        txtStudentId.clear();
        txtStudentName.clear();
        txtAddress.clear();
        txtContactNo.clear();
        dtDOB.getEditor().clear();
        cmbGender.getSelectionModel().clearSelection();

        try {
            loadAllStudents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean existsStudent(String id) throws Exception {
       return studentBO.studentExist(id);
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map,btnSave);;

            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            }
        }
    }

    public void backToMakeRegistrationOnAction(MouseEvent event) throws IOException {
        setUI(studentContext,"makeRegistrationForm");
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
