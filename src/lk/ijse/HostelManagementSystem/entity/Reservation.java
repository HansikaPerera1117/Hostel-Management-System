package lk.ijse.HostelManagementSystem.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reservation implements SuperEntity{
    @Id
    private String res_id;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private String status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_type_id")
    private  Room room;

    public Reservation(String res_id, LocalDate date, String status, String student_id, String room_type_id) {
        this.res_id = res_id;
        this.date = date;
        this.status = status;
        this.student.setStudent_id(student_id);
        this.room.setRoom_type_id(room_type_id);
    }
}
