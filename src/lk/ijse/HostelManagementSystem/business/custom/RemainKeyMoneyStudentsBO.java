package lk.ijse.HostelManagementSystem.business.custom;

import lk.ijse.HostelManagementSystem.business.SuperBO;
import lk.ijse.HostelManagementSystem.dto.CustomDTO;

import java.util.List;

public interface RemainKeyMoneyStudentsBO extends SuperBO {
    List<CustomDTO> getAllRemainingKeyMoneyStudents() throws Exception;
}
