package lk.ijse.HostelManagementSystem.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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

public class LogInFormController {

    public AnchorPane logInContext;
    public JFXTextField txtUserName;
    public JFXPasswordField pwdPassword;
    private int attempts = 0;
    private String userName;
    private String passWord;

    private final UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    public void btnLogInOnAction(ActionEvent actionEvent) throws IOException {
       try {
            List<UserDTO> allUsers = userBO.getAllUsers();
            for (UserDTO dto:allUsers) {
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
