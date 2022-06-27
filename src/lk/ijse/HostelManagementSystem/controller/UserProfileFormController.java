package lk.ijse.HostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.HostelManagementSystem.business.BOFactory;
import lk.ijse.HostelManagementSystem.business.SuperBO;
import lk.ijse.HostelManagementSystem.business.custom.UserBO;
import lk.ijse.HostelManagementSystem.dto.UserDTO;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class UserProfileFormController {
    public AnchorPane userProfileContext;
    public Label lblDate;
    public Label lblTime;
    public JFXPasswordField pwdPassword;
    public JFXTextField txtUserName;
    public JFXTextField txtEmail;
    public JFXPasswordField pwdNewPassword;
    public JFXTextField txtChangePasswordUserName;
    public JFXButton btnResetPassword;

    private final UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);
    public void initialize(){
        loadDateAndTime();
        initialUI();
    }

    private void initialUI() {
        try {
            List<UserDTO> allUsers = userBO.getAllUsers();
            for (UserDTO dto:allUsers) {
                txtUserName.setText(dto.getUserName());
                txtChangePasswordUserName.setText(dto.getUserName());
                pwdPassword.setText(dto.getPassWord());
                txtEmail.setText(dto.getEmail());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtUserName.setEditable(false);
        txtChangePasswordUserName.setEditable(false);
        pwdPassword.setEditable(false);
        txtEmail.setEditable(false);


    }

    public void btnResetPasswordOnAction(ActionEvent actionEvent) {
        try {
            if (userBO.updateUserDetails(new UserDTO(txtUserName.getText(),pwdNewPassword.getText(),txtEmail.getText()))){
                Notifications notifications = Notifications.create().title("Successful !").text("Password has been reset successfully...").hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
                notifications.darkStyle();
                notifications.show();
                pwdPassword.setText(pwdNewPassword.getText());
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to reset the password " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void btnDeleteUserOnAction(ActionEvent actionEvent) {
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
    }

    public void backToDashBoardOnAction(MouseEvent event) throws IOException {
        setUI(userProfileContext,"DashBoardForm");
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