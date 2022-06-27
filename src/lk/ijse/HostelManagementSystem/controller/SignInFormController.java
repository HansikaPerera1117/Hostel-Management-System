package lk.ijse.HostelManagementSystem.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.HostelManagementSystem.business.BOFactory;
import lk.ijse.HostelManagementSystem.business.custom.UserBO;
import lk.ijse.HostelManagementSystem.dto.UserDTO;
import org.controlsfx.control.Notifications;

import java.io.IOException;

public class SignInFormController {
    public JFXTextField txtEmail;
    public JFXTextField txtFullName;
    public JFXTextField txtUserName;
    public JFXPasswordField pwdPassword;
    public AnchorPane registerContext;

    private final UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);


    public void btnRegisterOnAction(ActionEvent actionEvent) {
      try {
            if (existsEmail(txtEmail.getText())) {
                new Alert(Alert.AlertType.ERROR, txtEmail.getText() + " already exists").show();
            }else {
                if (userBO.registerUser(new UserDTO(txtUserName.getText(), pwdPassword.getText(), txtEmail.getText()))) {
                    Notifications notifications = Notifications.create().title("Successful !").text("User has been registered successfully...").hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
                    notifications.darkStyle();
                    notifications.show();
                }
            }
        } catch (Exception e) {
          new Alert(Alert.AlertType.ERROR, "Failed to register the user " + e.getMessage()).show();
          e.printStackTrace();
        }
    }

    private boolean existsEmail(String email) throws Exception {
        return userBO.emailExist(email);
    }

    public void backToLogInOnAction(MouseEvent event) throws IOException {
        setUI(registerContext,"logInForm");
    }

    public void setUI(AnchorPane ap, String location) throws IOException {
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/HostelManagementSystem/view/"+location+".fxml"))));
        stage.setTitle(location);
        stage.centerOnScreen();
    }

}
