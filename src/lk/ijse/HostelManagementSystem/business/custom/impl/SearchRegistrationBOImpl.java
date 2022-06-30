package lk.ijse.HostelManagementSystem.business.custom.impl;

import lk.ijse.HostelManagementSystem.business.custom.SearchRegistrationBO;
import lk.ijse.HostelManagementSystem.dto.ReservationDTO;
import lk.ijse.HostelManagementSystem.dto.RoomDTO;
import lk.ijse.HostelManagementSystem.dto.StudentDTO;
import lk.ijse.HostelManagementSystem.entity.Reservation;
import lk.ijse.HostelManagementSystem.entity.Room;
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
    public boolean updateReservation(ReservationDTO reservationDTO) throws Exception {
       return reservationDAO.update(new Reservation(reservationDTO.getRes_id(),reservationDTO.getDate(),reservationDTO.getStatus(),reservationDTO.getStudent(),reservationDTO.getRoom()));
    }

    @Override
    public boolean deleteReservation(String id) throws Exception {

        //-------search and update availableRoomQty-------------------

        List<ReservationDTO> DeletedReservationDetails = searchReservationDetails(id);
        for (ReservationDTO dto : DeletedReservationDetails) {
            List<RoomDTO> room = searchRoomDetails(dto.getRoom().getRoom_type_id());
            for (RoomDTO roomDTO : room) {
                roomDTO.setAvailableRoomQty(roomDTO.getAvailableRoomQty()+1);

                //-------------------update Room----------------------------
                roomDAO.update(new Room(roomDTO.getRoom_type_id(),roomDTO.getType(),roomDTO.getKey_money(),roomDTO.getQty(),roomDTO.getAvailableRoomQty()));

            }
        }

        return reservationDAO.delete(id);

    }

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
    public List<RoomDTO> getAllRooms() throws Exception {
        List<Room> all = roomDAO.findAll();
        List<RoomDTO>roomDTOList = new ArrayList<>();
        for (Room room : all) {
            roomDTOList.add(new RoomDTO(room.getRoom_type_id(),room.getType(),room.getKey_money(),room.getQty(),room.getAvailableRoomQty()));
        }
        return roomDTOList;
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
        List<Reservation> reservationList = reservationDAO.getAllReservationsAccordingToStudent(id);
        List<ReservationDTO>reservationDTOS = new ArrayList<>();
        for (Reservation reservation:reservationList) {
            reservationDTOS.add(new ReservationDTO(reservation.getRes_id(),reservation.getDate(),reservation.getStudent(),reservation.getRoom(),reservation.getStatus()));
        }
        return reservationDTOS;
    }

    @Override
    public List<ReservationDTO> getAllReservationsAccordingToRoom(String id) throws Exception {
        List<Reservation> allReservationsAccordingToRoom = reservationDAO.getAllReservationsAccordingToRoom(id);
        List<ReservationDTO>reservationDTOS = new ArrayList<>();
        for (Reservation reservation:allReservationsAccordingToRoom) {
            reservationDTOS.add(new ReservationDTO(reservation.getRes_id(),reservation.getDate(),reservation.getStudent(),reservation.getRoom(),reservation.getStatus()));
        }
        return reservationDTOS;
    }

    @Override
    public List<ReservationDTO> searchReservationDetails(String id) throws Exception {
        List<Reservation> reservationList = reservationDAO.find(id);
        List<ReservationDTO>reservationDTOS = new ArrayList<>();
        for (Reservation reservation:reservationList) {
            reservationDTOS.add(new ReservationDTO(reservation.getRes_id(),reservation.getDate(),reservation.getStudent(),reservation.getRoom(),reservation.getStatus()));
        }
        return reservationDTOS;
    }

    @Override
    public List<RoomDTO> searchRoomDetails(String id) throws Exception {
        List<Room> roomList = roomDAO.find(id);
        List<RoomDTO>detailList = new ArrayList<>();
        for (Room room : roomList) {
            detailList.add(new RoomDTO(room.getRoom_type_id(),room.getType(),room.getKey_money(),room.getQty(),room.getAvailableRoomQty()));
        }
        return detailList;
    }

    @Override
    public List<StudentDTO> searchStudentDetails(String id) throws Exception {
        List<Student> students = studentDAO.find(id);
        List<StudentDTO> detailList = new ArrayList<>();
        for (Student student : students) {
            detailList.add(new StudentDTO(student.getStudent_id(),student.getName(),student.getAddress(),student.getContact_no(),student.getDob(),student.getGender()));
        }
        return detailList;
    }

    @Override
    public boolean reservationExist(String id) throws Exception {
       return reservationDAO.exist(id);
    }


}
