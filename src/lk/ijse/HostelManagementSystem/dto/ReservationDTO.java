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

    private int ACRoomCount ;
    private int ACFoodRoomCount ;
    private int NonACRoomCount ;
    private int NonACFoodRoomCount ;

    public ReservationDTO(String resID, LocalDate date, Student student, Room room, String status) {
        this.res_id = resID;
        this.date = date;
        this.student = student;
        this.room = room;
        this.status = status;
    }
}
