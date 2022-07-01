package lk.ijse.HostelManagementSystem.business.custom.impl;

import lk.ijse.HostelManagementSystem.business.custom.ReservationBO;
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

public class ReservationBOImpl implements ReservationBO {
    private final RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ROOM);
    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.STUDENT);
    private final ReservationDAO reservationDAO = (ReservationDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.RESERVATION);


    @Override
    public boolean addReservation(ReservationDTO reservationDTO) throws Exception {
        //---------------------------------search and update available room qty-------------------------------
        String room_type_id = reservationDTO.getRoom().getRoom_type_id();
        List<RoomDTO> roomDTOList = searchRoom(room_type_id);
        for (RoomDTO dto : roomDTOList) {
            dto.setAvailableRoomQty(dto.getAvailableRoomQty()-1);

            //-------------------update Room----------------------------
            roomDAO.update(new Room(dto.getRoom_type_id(),dto.getType(),dto.getKey_money(),dto.getQty(),dto.getAvailableRoomQty()));

        }

        return reservationDAO.add(new Reservation(reservationDTO.getRes_id(),reservationDTO.getDate(),reservationDTO.getStatus(),reservationDTO.getStudent(),reservationDTO.getRoom()));
    }

    @Override
    public List<StudentDTO> searchStudent(String id) throws Exception {
        List<Student> students = studentDAO.find(id);
        List<StudentDTO> studentList = new ArrayList<>();
        for (Student student: students) {
            studentList.add(new StudentDTO(student.getStudent_id(),student.getName(),student.getAddress(),student.getContact_no(),student.getDob(),student.getGender()));
        }
        return studentList;
    }

    @Override
    public List<RoomDTO> searchRoom(String id) throws Exception {
        List<Room> all = roomDAO.find(id);
        List<RoomDTO> roomDTOList = new ArrayList<>();
        for (Room room: all) {
            roomDTOList.add(new RoomDTO(room.getRoom_type_id(),room.getType(),room.getKey_money(),room.getQty(),room.getAvailableRoomQty()));
        }
        return roomDTOList;
    }

    @Override
    public List<ReservationDTO> searchReservation(String id) throws Exception {
        List<Reservation> reservationList = reservationDAO.find(id);
        List<ReservationDTO>reservationDTOS = new ArrayList<>();
        for (Reservation reservation:reservationList) {
            reservationDTOS.add(new ReservationDTO(reservation.getRes_id(),reservation.getDate(),reservation.getStudent(),reservation.getRoom(),reservation.getStatus()));
        }
        return reservationDTOS;
    }

    @Override
    public boolean checkStudentIsAvailable(String id) throws Exception {
        return studentDAO.exist(id);
    }

    @Override
    public boolean checkRoomIsAvailable(String id) throws Exception {
        return roomDAO.exist(id);
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
        List<RoomDTO> roomList = new ArrayList<>();
        for (Room room : all) {
            roomList.add(new RoomDTO(room.getRoom_type_id(), room.getType(), room.getKey_money(), room.getQty(),room.getAvailableRoomQty()));
        }
        return roomList;
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
    public String generateNewReservationId() throws Exception {
       return reservationDAO.generateNewReservationId();
    }

    @Override
    public List<ReservationDTO> remainKeyMoneyStudents() throws Exception {
        List<Reservation> reservationList = reservationDAO.remainKeyMoneyStudents();
        List<ReservationDTO>reservationDTOS = new ArrayList<>();
        for (Reservation reservation:reservationList) {
            reservationDTOS.add(new ReservationDTO(reservation.getRes_id(),reservation.getDate(),reservation.getStudent(),reservation.getRoom(),reservation.getStatus()));
        }
            return reservationDTOS;
    }

}
