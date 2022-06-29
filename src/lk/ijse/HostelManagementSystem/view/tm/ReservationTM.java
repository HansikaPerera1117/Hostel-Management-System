package lk.ijse.HostelManagementSystem.view.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationTM {
    private String res_id;
    private LocalDate date;
    private String student_id;
    private String room_type_id;
    private String status;
    private Button btn;

    public ReservationTM(String resID, LocalDate date, String student_id, String room_type_id, String status) {
        this.res_id = resID;
        this.date = date;
        this.student_id = student_id;
        this.room_type_id = room_type_id;
        this.status = status;
    }

}
