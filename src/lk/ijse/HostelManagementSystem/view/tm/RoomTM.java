package lk.ijse.HostelManagementSystem.view.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomTM {
    private String room_type_id;
    private String type;
    private String key_money;
    private int qty;
    private int availableRoomQty;
    private Button btn;
}
