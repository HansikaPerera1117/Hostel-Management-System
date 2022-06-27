package lk.ijse.HostelManagementSystem.controller;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class DashBoardFormController {
    public AnchorPane dashBoardContext;
    public Label lblMenu;
    public Label lblDescription;
    public ImageView imgAddRegistration;
    public ImageView imgManageRoom;
    public ImageView imgSearchRegistration;
    public ImageView imgRemainKeyMoney;

    public void MouseEnteredOnAction(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            switch (icon.getId()) {
                case "imgAddRegistration":
                    lblMenu.setText("Make Registration");
                    lblDescription.setText("Click if you want to make registrations");
                    break;
                case "imgManageRoom":
                    lblMenu.setText("Manage Rooms");
                    lblDescription.setText("Click to add, update, delete, search or view rooms");
                    break;
                case "imgSearchRegistration":
                    lblMenu.setText("Search Registration");
                    lblDescription.setText("Click if you want to search registrations");
                    break;
                case "imgRemainKeyMoney":
                    lblMenu.setText("Remaining Key money Student ");
                    lblDescription.setText("Click if you want to see list of students who Remaining Key money");
                    break;
            }
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }


    public void MouseExitedOnAction(MouseEvent event) {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblMenu.setText("Welcome");
            lblDescription.setText("Please select one of main operations to proceed");
        }
    }

    public void navigationOnAction(MouseEvent event) throws IOException {
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            switch (icon.getId()) {
                case "imgAddRegistration":
                    setUI(dashBoardContext,"makeRegistrationForm");
                    break;
                case "imgManageRoom":
                    setUI(dashBoardContext,"manageRoomForm");
                    break;
                case "imgSearchRegistration":
                    setUI(dashBoardContext,"searchRegistrationForm");
                    break;
                case "imgRemainKeyMoney":
                    setUI(dashBoardContext,"remainKeyMoneyStudentForm");
                    break;
            }
        }
    }

    public void openMyProfileOnAction(MouseEvent event) throws IOException {
        setUI(dashBoardContext,"userProfileForm");
    }

    public void logOutOnAction(MouseEvent event) throws IOException {
        setUI(dashBoardContext,"logInForm");
    }

    public void setUI(AnchorPane ap, String location) throws IOException {
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/HostelManagementSystem/view/"+location+".fxml"))));
        stage.setTitle(location);
        stage.centerOnScreen();
    }

}
