package lk.ijse.HostelManagementSystem.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reservation implements SuperEntity{
    @Id
    private String res_id;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private String status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_type_id")
    private  Room room;
}
