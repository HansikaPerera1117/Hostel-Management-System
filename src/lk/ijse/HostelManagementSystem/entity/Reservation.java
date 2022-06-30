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

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private  Room room;

}
