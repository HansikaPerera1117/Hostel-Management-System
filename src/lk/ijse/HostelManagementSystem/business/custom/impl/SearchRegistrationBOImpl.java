package lk.ijse.HostelManagementSystem.business.custom.impl;

import lk.ijse.HostelManagementSystem.business.custom.SearchRegistrationBO;
import lk.ijse.HostelManagementSystem.dto.ReservationDTO;
import lk.ijse.HostelManagementSystem.dto.StudentDTO;
import lk.ijse.HostelManagementSystem.entity.Reservation;
import lk.ijse.HostelManagementSystem.entity.Student;
import lk.ijse.HostelManagementSystem.repository.DAOFactory;
import lk.ijse.HostelManagementSystem.repository.custom.ReservationDAO;
import lk.ijse.HostelManagementSystem.repository.custom.RoomDAO;
import lk.ijse.HostelManagementSystem.repository.custom.StudentDAO;

import java.util.ArrayList;
import java.util.List;

public class SearchRegistrationBOImpl implements SearchRegistrationBO {
    private final RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ROOM);
    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.STUDENT);
    private final ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.RESERVATION);


    @Override
    public List<StudentDTO> getAllStudents() throws Exception {
        List<Student> all = studentDAO.findAll();
        List<StudentDTO>studentList = new ArrayList<>();
        for (Student student:all) {
            studentList.add(new StudentDTO(student.getStudent_id(),student.getName(),student.getAddress(),student.getContact_no(),student.getDob(),student.getGender()));
        }
        return studentList;
    }

    @Override
    public List<ReservationDTO> getAllReservations() throws Exception {
        List<Reservation> all = reservationDAO.findAll();
        List<ReservationDTO>reservationDTOS = new ArrayList<>();
        for (Reservation reservation:all) {
            reservationDTOS.add(new ReservationDTO(reservation.getRes_id(),reservation.getDate(),reservation.getStudent(),reservation.getRoom(),reservation.getStatus()));
        }
        return reservationDTOS;
    }

    @Override
    public List<ReservationDTO> getAllReservationsAccordingToStudent(String id) throws Exception {
        List<Reservation> reservationList = reservationDAO.find(id);
        List<ReservationDTO>reservationDTOS = new ArrayList<>();
        for (Reservation reservation:reservationList) {
            reservationDTOS.add(new ReservationDTO(reservation.getRes_id(),reservation.getDate(),reservation.getStudent(),reservation.getRoom(),reservation.getStatus()));
        }
        return reservationDTOS;
    }


}
