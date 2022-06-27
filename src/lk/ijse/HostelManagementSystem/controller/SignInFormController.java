package lk.ijse.HostelManagementSystem.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignInFormController {
    public JFXTextField txtEmail;
    public JFXTextField txtFullName;
    public JFXTextField txtUserName;
    public JFXPasswordField pwdPassword;

    public AnchorPane registerContext;


    public void btnRegisterOnAction(ActionEvent actionEvent) {
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
