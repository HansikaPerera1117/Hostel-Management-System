package lk.ijse.HostelManagementSystem.business.custom;

import lk.ijse.HostelManagementSystem.business.SuperBO;
import lk.ijse.HostelManagementSystem.dto.RoomDTO;
import lk.ijse.HostelManagementSystem.dto.StudentDTO;
import lk.ijse.HostelManagementSystem.entity.Student;

import java.util.List;

public interface ReservationBO extends SuperBO {
     List<StudentDTO> searchStudent(String id) throws Exception;

     List<RoomDTO> searchRoom(String id) throws Exception;

     boolean checkStudentIsAvailable(String id) throws Exception;

     boolean checkRoomIsAvailable(String id) throws Exception;

     List<StudentDTO> getAllStudents() throws Exception;

     List<RoomDTO> getAllRooms() throws Exception;
}
