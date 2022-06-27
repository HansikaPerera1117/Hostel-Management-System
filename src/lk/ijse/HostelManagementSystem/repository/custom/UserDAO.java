package lk.ijse.HostelManagementSystem.repository.custom;

import lk.ijse.HostelManagementSystem.entity.User;
import lk.ijse.HostelManagementSystem.repository.SuperDAO;

import java.util.List;

public interface UserDAO extends SuperDAO {
    List<User> getAll() throws Exception;
}
