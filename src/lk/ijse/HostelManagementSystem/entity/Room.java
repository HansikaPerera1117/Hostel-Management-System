package lk.ijse.HostelManagementSystem.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Room implements SuperEntity{
    @Id
    private String room_type_id;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String key_money;
    @Column(nullable = false)
    private int qty;

    @OneToMany(mappedBy = "room",cascade = CascadeType.ALL)
    private List<Reservation> studentList = new ArrayList<>();
}
