package lk.ijse.HostelManagementSystem.business.custom;

import lk.ijse.HostelManagementSystem.business.SuperBO;
import lk.ijse.HostelManagementSystem.dto.StudentDTO;

import java.util.List;

public interface StudentBO extends SuperBO {
    List<StudentDTO> getAllStudents() throws Exception;

    boolean addStudent(StudentDTO studentDTO) throws Exception;

    boolean updateStudent(StudentDTO studentDTO) throws Exception;

    boolean deleteStudent(String id) throws Exception;

    boolean studentExist(String id) throws Exception;

}
