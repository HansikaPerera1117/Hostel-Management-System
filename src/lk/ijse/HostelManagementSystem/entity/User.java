package lk.ijse.HostelManagementSystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User implements SuperEntity {
    @Id
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String passWord;
    @Column(nullable = false)
    private String email;

}
