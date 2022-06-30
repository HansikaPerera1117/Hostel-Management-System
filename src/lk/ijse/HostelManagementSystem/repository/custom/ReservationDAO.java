package lk.ijse.HostelManagementSystem.repository.custom;

import lk.ijse.HostelManagementSystem.entity.Reservation;
import lk.ijse.HostelManagementSystem.repository.CrudDAO;


import java.util.List;


public interface ReservationDAO extends CrudDAO<Reservation,String> {
     List<Reservation> getAllReservationsAccordingToStudent(String id) throws Exception;

     List<Reservation> getAllReservationsAccordingToRoom(String id) throws Exception;

     String generateNewReservationId() throws Exception;

     List<Reservation> remainKeyMoneyStudents()throws Exception;
}
