package lk.ijse.HostelManagementSystem.view.tm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomTM {
    private String student_id;
    private String name;
    private String address;
    private String contact_no;
    private LocalDate dob;
    private String gender;

    private String room_type_id;
    private String type;
    private String key_money;
    private int qty;

    private String res_id;
    private LocalDate date;
    //private String student_id;
    // private String room_type_id;
    private String status;


    public CustomTM(String student_id, String name, String contact_no, String res_id, String room_type_id, LocalDate date, String status) {
        this.student_id = student_id;
        this.name = name;
        this.contact_no = contact_no;
        this.res_id = res_id;
        this.room_type_id = room_type_id;
        this.date = date;
        this.status = status;
    }
}
