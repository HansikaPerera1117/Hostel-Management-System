package lk.ijse.HostelManagementSystem.business.custom;

import lk.ijse.HostelManagementSystem.business.SuperBO;
import lk.ijse.HostelManagementSystem.dto.ReservationDTO;
import lk.ijse.HostelManagementSystem.dto.RoomDTO;
import lk.ijse.HostelManagementSystem.dto.StudentDTO;

import java.util.List;

public interface SearchRegistrationBO extends SuperBO {
    boolean updateReservation(ReservationDTO reservationDTO) throws Exception;

    boolean deleteReservation(String id) throws Exception;

    List<StudentDTO> getAllStudents() throws Exception;

    List<RoomDTO> getAllRooms() throws Exception;

    List<ReservationDTO> getAllReservations() throws Exception;

    List<ReservationDTO> getAllReservationsAccordingToStudent(String id) throws Exception;

    List<ReservationDTO> getAllReservationsAccordingToRoom(String id) throws Exception;

    List<ReservationDTO> searchReservationDetails(String id) throws Exception;

    List<RoomDTO> searchRoomDetails(String id) throws Exception;

    List<StudentDTO> searchStudentDetails(String id) throws Exception;

    boolean reservationExist(String id) throws Exception;

}
