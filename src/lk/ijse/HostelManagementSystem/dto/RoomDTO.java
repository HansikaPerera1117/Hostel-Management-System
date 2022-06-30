package lk.ijse.HostelManagementSystem.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomDTO {
    private String room_type_id;
    private String type;
    private String key_money;
    private int qty;
    private int availableRoomQty;


}
