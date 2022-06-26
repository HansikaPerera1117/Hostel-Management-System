package lk.ijse.HostelManagementSystem.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomEntity {
    private String student_id;
    private String name;
    private String address;
    private String contact_no;
    private Date dob;
    private String gender;

    private String room_type_id;
    private String type;
    private String key_money;
    private int qty;

    private String res_id;
    private Date date;
    //private String student_id;
    // private String room_type_id;
    private String status;





}
