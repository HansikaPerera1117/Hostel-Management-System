package lk.ijse.HostelManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationDTO {
    private String res_id;
    private LocalDate date;
    private String student_id;
    private String room_type_id;
    private String status;
}
