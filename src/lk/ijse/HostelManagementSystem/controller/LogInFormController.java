package lk.ijse.HostelManagementSystem.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import java.util.List;
import java.util.Observable;

public class LogInFormController {

    public AnchorPane logInContext;
    public JFXTextField txtUserName;
    public JFXPasswordField pwdPassword;
    public JFXCheckBox chkPasswordShowOrHide;
    public JFXTextField txtPW;
    private int attempts = 0;
    public String userName;
    private String passWord;

    private final UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    public void initialize(){
        txtPW.setVisible(false);

    }

    public void btnLogInOnAction(ActionEvent actionEvent) throws IOException {
       try {
           List<UserDTO> userDetails = userBO.searchUser(txtUserName.getText());
           for (UserDTO dto:userDetails) {
                userName = dto.getUserName();
                passWord = dto.getPassWord();
           }
       } catch (Exception e) {
            e.printStackTrace();
        }

        attempts++;
        if (attempts<=3){
            if (txtUserName.getText().equals(userName) && pwdPassword.getText().equals(passWord)){
                Notifications notifications = Notifications.create().title("LogIn Successful !").text("You lodged in to the system  successfully...").hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
                notifications.darkStyle();
                notifications.show();

                setUI(logInContext,"DashBoardForm");
            }else if(txtUserName.getText().equals("") && pwdPassword.getText().equals("")){
                Notifications notifications = Notifications.create().title("Data Required !").text("Please enter your Username and Password...").hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
                notifications.darkStyle();
                notifications.show();
            }else{
                Notifications notifications = Notifications.create().title("LogIn Unsuccessful !").text("Please check and  Try again!").hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
                notifications.darkStyle();
                notifications.show();
            }
        }else{
            txtUserName.setEditable(false);
            pwdPassword.setEditable(false);
        }

    }

    public void chkPasswordShowOrHideMouseClickedOnAction(MouseEvent event) {
        String passwordText = pwdPassword.getText();
        chkPasswordShowOrHide.selectedProperty().addListener((ObservableValue<? extends Boolean> observableValue, Boolean oldValue,Boolean newValue) ->{
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

    public void openRegisterOnAction(MouseEvent event) throws IOException {
        setUI(logInContext,"signInForm");
    }

    public void setUI(AnchorPane ap, String location) throws IOException {
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/HostelManagementSystem/view/"+location+".fxml"))));
        stage.setTitle(location);
        stage.centerOnScreen();
    }

}
