package lk.ijse.HostelManagementSystem.dto;

import lk.ijse.HostelManagementSystem.entity.Room;
import lk.ijse.HostelManagementSystem.entity.Student;
import lk.ijse.HostelManagementSystem.entity.SuperEntity;
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
    private Student student;
    private Room room;
    private String status;


}
