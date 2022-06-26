package lk.ijse.HostelManagementSystem.view.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationTM {
    private String res_id;
    private Date date;
    private String student_id;
    private String room_type_id;
    private String status;
    private Button btn;
}
