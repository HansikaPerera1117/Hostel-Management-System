package lk.ijse.HostelManagementSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Student implements SuperEntity {
    @Id
    private String student_id;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT",nullable = false)
    private String address;
    @Column(nullable = false)
    private String contact_no;
    @Column(nullable = false)
    private LocalDate dob;
    @Column(nullable = false)
    private String gender;

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private List<Reservation> roomList = new ArrayList<>();

    public Student(String student_id, String name, String address, String contact_no, LocalDate dob, String gender) {
        this.student_id = student_id;
        this.name = name;
        this.address = address;
        this.contact_no = contact_no;
        this.dob = dob;
        this.gender = gender;

    }
}
