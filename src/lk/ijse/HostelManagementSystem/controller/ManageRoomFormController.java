package lk.ijse.HostelManagementSystem.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import lk.ijse.HostelManagementSystem.business.custom.RoomBO;
import lk.ijse.HostelManagementSystem.dto.RoomDTO;
import lk.ijse.HostelManagementSystem.validation.ValidationUtil;
import lk.ijse.HostelManagementSystem.view.tm.RoomTM;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ManageRoomFormController {
    public AnchorPane roomsContext;
    public JFXTextField txtRoomTypeID;
    public JFXTextField txtKeyMoney;
    public JFXTextField txtRoomType;
    public JFXTextField txtQty;
    public JFXTextField txtAvailableQty;
    public TableView<RoomTM> tblRoom;
    public TableColumn colId;
    public TableColumn colType;
    public TableColumn colKeyMoney;
    public TableColumn colQty;
    public TableColumn colAvailableQty;
    public TableColumn colDelete;
    public JFXButton btnSave;
    public JFXButton btnAddNewRoom;
    public Label lblDate;
    public Label lblTime;

    private final RoomBO roomBO = (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ROOM);


    LinkedHashMap<TextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){

        colId.setCellValueFactory(new PropertyValueFactory<>("room_type_id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("key_money"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colAvailableQty.setCellValueFactory(new PropertyValueFactory<>("availableRoomQty"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));

        tblRoom.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null){
                txtRoomTypeID.setText(newValue.getRoom_type_id());
                txtRoomTypeID.setEditable(false);
                txtRoomType.setText(newValue.getType());
                txtKeyMoney.setText(newValue.getKey_money());
                txtQty.setText(String.valueOf(newValue.getQty()));
                txtAvailableQty.setText(String.valueOf(newValue.getAvailableRoomQty()));

                txtRoomTypeID.setDisable(false);
                txtRoomType.setDisable(false);
                txtKeyMoney.setDisable(false);
                txtQty.setDisable(false);
                txtAvailableQty.setDisable(false);
            }
        });

        loadDateAndTime();
        initialUI();

        //----------------------Validation----------------------------------
        Pattern roomTypeIdPattern = Pattern.compile("^(RM-)[0-9]{4}$");
        Pattern roomTypePattern = Pattern.compile("^(Non-AC|Non-AC/Food|AC|AC/Food)$");
        Pattern keyMoneyPattern = Pattern.compile("^[1-9][0-9]*(.[0-9]{1,2})?$");
        Pattern qtyPattern = Pattern.compile("^[1-9][0-9]{0,2}$");
        Pattern availableQtyPattern = Pattern.compile("^[1-9][0-9]{0,2}$");

        map.put(txtRoomTypeID,roomTypeIdPattern);
        map.put(txtRoomType,roomTypePattern);
        map.put(txtKeyMoney,keyMoneyPattern);
        map.put(txtQty,qtyPattern);
        map.put(txtAvailableQty,availableQtyPattern);


        try {
            loadAllRooms();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialUI() {
        txtRoomTypeID.clear();
        txtRoomType.clear();
        txtKeyMoney.clear();
        txtQty.clear();
        txtAvailableQty.clear();
        txtRoomTypeID.setDisable(true);
        txtRoomType.setDisable(true);
        txtKeyMoney.setDisable(true);
        txtQty.setDisable(true);
        txtAvailableQty.setDisable(true);
        btnSave.setDisable(true);
    }

    private void loadAllRooms() throws Exception {
        tblRoom.getItems().clear();

        List<RoomDTO> allRooms = roomBO.getAllRooms();
        for (RoomDTO dto:allRooms) {
            Button btn = new Button("Delete");
            tblRoom.getItems().add(new RoomTM(dto.getRoom_type_id(),dto.getType(),dto.getKey_money(),dto.getQty(),dto.getAvailableRoomQty(),btn));

            btn.setOnAction(e->{
            //------------------delete room--------------------------------

                String room_type_id = tblRoom.getSelectionModel().getSelectedItem().getRoom_type_id();

                try {
                    if (!existsRoom(room_type_id)){
                        new Alert(Alert.AlertType.ERROR, "There is no such room associated with the id " + room_type_id).show();
                    }

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure?", ButtonType.YES, ButtonType.NO);
                    Optional<ButtonType> buttonType = alert.showAndWait();

                    if (buttonType.get().equals(ButtonType.YES)) {
                        roomBO.deleteRoom(room_type_id);
                        tblRoom.getItems().remove(tblRoom.getSelectionModel().getSelectedItem());
                        tblRoom.getSelectionModel().clearSelection();
                        initialUI();

                        Notifications notifications = Notifications.create().title("Successful !").text("Room has been deleted successfully...").hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
                        notifications.darkStyle();
                        notifications.show();

                    }

                } catch (Exception exception) {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete the room " + room_type_id).show();
                   exception.printStackTrace();
                }
            });
        }
    }

    public void btnNewRoomOnAction(ActionEvent actionEvent) {
        txtRoomTypeID.setEditable(true);
        txtRoomTypeID.setDisable(false);
        txtRoomType.setDisable(false);
        txtKeyMoney.setDisable(false);
        txtQty.setDisable(false);
        txtAvailableQty.setDisable(false);
        txtRoomTypeID.clear();
        txtRoomType.clear();
        txtKeyMoney.clear();
        txtQty.clear();
        txtAvailableQty.clear();
        txtRoomTypeID.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblRoom.getSelectionModel().clearSelection();
    }

    private boolean existsRoom(String room_type_id) throws Exception {
        return roomBO.roomExist(room_type_id);
    }

    public void btnSaveRoomOnAction(ActionEvent actionEvent) {
        if (btnSave.getText().equalsIgnoreCase("Save")){
            //---------------------save room-------------------------------------------
            try {
                if (existsRoom(txtRoomTypeID.getText())){
                    new Alert(Alert.AlertType.ERROR, txtRoomTypeID.getText() + " already exists").show();
                }else {

                    if (roomBO.addRoom(new RoomDTO(txtRoomTypeID.getText(),txtRoomType.getText(),txtKeyMoney.getText(),Integer.parseInt(txtQty.getText()),Integer.parseInt(txtAvailableQty.getText())))) {
                        Notifications notifications = Notifications.create().title("Successful !").text("Room has been saved successfully...").hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
                        notifications.darkStyle();
                        notifications.show();
                    }
                }

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the room " + e.getMessage()).show();
                e.printStackTrace();
            }

        }else {
            //---------------------update room-------------------------------------------
            try {

                if (!existsRoom(txtRoomTypeID.getText())){
                    new Alert(Alert.AlertType.ERROR, "There is no such room associated with the id " + txtRoomTypeID.getText() ).show();
                }else {
                    if (roomBO.updateRoom(new RoomDTO(txtRoomTypeID.getText(), txtRoomType.getText(), txtKeyMoney.getText(), Integer.parseInt(txtQty.getText()),Integer.parseInt(txtAvailableQty.getText())))) {
                        Notifications notifications = Notifications.create().title("Successful !").text("Room has been updated successfully...").hideAfter(Duration.seconds(5)).position(Pos.BOTTOM_RIGHT);
                        notifications.darkStyle();
                        notifications.show();
                    }
                }

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the room " + e.getMessage()).show();
                e.printStackTrace();
            }
        }
        txtRoomTypeID.clear();
        txtRoomType.clear();
        txtKeyMoney.clear();
        txtQty.clear();
        txtAvailableQty.clear();

        try {
            loadAllRooms();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void backToDashBoardOnAction(MouseEvent event) throws IOException {
        setUI(roomsContext,"DashBoardForm");
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
