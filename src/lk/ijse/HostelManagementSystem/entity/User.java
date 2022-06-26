package lk.ijse.HostelManagementSystem.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    String userName;
    String passWord;
}
