package lk.ijse.HostelManagementSystem.business.custom;

import lk.ijse.HostelManagementSystem.business.SuperBO;
import lk.ijse.HostelManagementSystem.dto.ReservationDTO;
import lk.ijse.HostelManagementSystem.dto.RoomDTO;
import lk.ijse.HostelManagementSystem.dto.StudentDTO;

import java.util.List;

public interface SearchRegistrationBO extends SuperBO {
    List<StudentDTO> getAllStudents() throws Exception;

    List<ReservationDTO> getAllReservations() throws Exception;

    List<ReservationDTO> getAllReservationsAccordingToStudent(String id) throws Exception;

}
