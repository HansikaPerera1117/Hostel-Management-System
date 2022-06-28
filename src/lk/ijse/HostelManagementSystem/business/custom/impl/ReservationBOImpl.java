package lk.ijse.HostelManagementSystem.business.custom.impl;

import lk.ijse.HostelManagementSystem.business.custom.ReservationBO;
import lk.ijse.HostelManagementSystem.dto.RoomDTO;
import lk.ijse.HostelManagementSystem.dto.StudentDTO;
import lk.ijse.HostelManagementSystem.entity.Room;
import lk.ijse.HostelManagementSystem.entity.Student;
import lk.ijse.HostelManagementSystem.repository.DAOFactory;
import lk.ijse.HostelManagementSystem.repository.custom.RoomDAO;
import lk.ijse.HostelManagementSystem.repository.custom.StudentDAO;

import java.util.ArrayList;
import java.util.List;

public class ReservationBOImpl implements ReservationBO {
    private final RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ROOM);
    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.STUDENT);

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
        return null;
    }

    @Override
    public boolean checkStudentIsAvailable(String id) throws Exception {
        return studentDAO.exist(id);
    }

    @Override
    public boolean checkRoomIsAvailable(String id) throws Exception {
        return false;
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
            roomList.add(new RoomDTO(room.getRoom_type_id(), room.getType(), room.getKey_money(), room.getQty()));
        }
        return roomList;
    }
}
