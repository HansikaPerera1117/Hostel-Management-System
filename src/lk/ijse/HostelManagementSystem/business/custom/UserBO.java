package lk.ijse.HostelManagementSystem.business.custom;

import lk.ijse.HostelManagementSystem.business.SuperBO;
import lk.ijse.HostelManagementSystem.dto.StudentDTO;
import lk.ijse.HostelManagementSystem.dto.UserDTO;

import java.util.List;

public interface UserBO extends SuperBO {
    List<UserDTO> getAllUsers() throws Exception;

    List<UserDTO> searchUser(String username) throws Exception;

    boolean registerUser(UserDTO userDTO) throws Exception;

    boolean updateUserDetails(UserDTO userDTO) throws Exception;

    boolean deleteUserAccount(String username) throws Exception;

    boolean emailExist(String email) throws Exception;
}
