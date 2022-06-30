package lk.ijse.HostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.HostelManagementSystem.business.BOFactory;
import lk.ijse.HostelManagementSystem.business.SuperBO;
import lk.ijse.HostelManagementSystem.business.custom.UserBO;
import lk.ijse.HostelManagementSystem.dto.UserDTO;
import lk.ijse.HostelManagementSystem.validation.ValidationUtil;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class UserProfileFormController {
    public AnchorPane userProfileContext;
    public Label lblDate;
    public Label lblTime;
    public JFXPasswordField pwdPassword;
    public JFXTextField txtUserName;
    public JFXTextField txtEmail;
    public JFXPasswordField pwdNewPassword;
    public JFXTextField txtPW;
    public JFXTextField txtNewPW;
    public JFXCheckBox chkPasswordShowOrHide;
    public JFXCheckBox chkNewPasswordShowOrHide;
    public JFXTextField txtChangePasswordUserName;
    public JFXButton btnResetPassword;

    private final UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){
        txtPW.setVisible(false);
        txtNewPW.setVisible(false);
        //------------validation--------------------
        Pattern passwordPattern = Pattern.compile("^[A-z0-9]{4,8}$");
        map.put(pwdNewPassword,passwordPattern);

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
        pwdNewPassword.setEditable(true);
        btnResetPassword.setDisable(true);

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

    public void chkPasswordShowOrHideMouseClickedOnAction(MouseEvent event) {
        String passwordText = pwdPassword.getText();
        chkPasswordShowOrHide.selectedProperty().addListener((ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) ->{
            if(chkPasswordShowOrHide.isSelected()){
                chkPasswordShowOrHide.setText("Hide Password");
                txtPW.setText(passwordText);
                txtPW.setVisible(true);
                pwdPassword.setVisible(false);
                return;
            }else {
                chkPasswordShowOrHide.setText("Show Password");
                pwdPassword.setText(passwordText);
                pwdPassword.setVisible(true);
                txtPW.setVisible(false);
            }
        } );
    }

    public void chkNewPasswordShowOrHideMouseClickedOnAction(MouseEvent event) {
        String NewPasswordText = pwdNewPassword.getText();
        chkNewPasswordShowOrHide.selectedProperty().addListener((ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) ->{
            if(chkNewPasswordShowOrHide.isSelected()){
                chkNewPasswordShowOrHide.setText("Hide Password");
                txtNewPW.setText(NewPasswordText);
                txtNewPW.setVisible(true);
                pwdNewPassword.setVisible(false);
                return;
            }else {
                chkNewPasswordShowOrHide.setText("Show Password");
                pwdNewPassword.setText(NewPasswordText);
                pwdNewPassword.setVisible(true);
                txtNewPW.setVisible(false);
            }
        } );
    }

    public void btnDeleteUserOnAction(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure?", ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();

            if (buttonType.get().equals(ButtonType.YES)) {
                userBO.deleteUserAccount(txtUserName.getText());

                txtUserName.clear();
                pwdPassword.clear();
                txtEmail.clear();
                txtChangePasswordUserName.clear();
                pwdNewPassword.clear();
                pwdNewPassword.setEditable(false);

                Notifications notifications = Notifications.create().title("Successful !").text("User has been deleted successfully...").hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
                notifications.darkStyle();
                notifications.show();

                setUI(userProfileContext,"logInForm");
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the user ").show();
            e.printStackTrace();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnResetPassword);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map,btnResetPassword);;

            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            }
        }
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
