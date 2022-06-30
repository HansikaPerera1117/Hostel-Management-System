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
    @Column(nullable = false)
    private int availableRoomQty;


    @OneToMany(mappedBy = "room",cascade = CascadeType.ALL)
    private List<Reservation> studentList = new ArrayList<>();

    public Room(String room_type_id, String type, String key_money, int qty, int availableRoomQty) {
        this.room_type_id = room_type_id;
        this.type = type;
        this.key_money = key_money;
        this.qty = qty;
        this.availableRoomQty = availableRoomQty;
    }

}
