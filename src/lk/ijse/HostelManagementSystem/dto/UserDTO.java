package lk.ijse.HostelManagementSystem.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private String userName;
    private String passWord;
    private String email;

}
