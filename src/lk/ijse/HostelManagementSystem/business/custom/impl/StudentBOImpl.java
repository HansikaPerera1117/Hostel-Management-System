package lk.ijse.HostelManagementSystem.business.custom.impl;

import lk.ijse.HostelManagementSystem.business.custom.StudentBO;
import lk.ijse.HostelManagementSystem.dto.StudentDTO;
import lk.ijse.HostelManagementSystem.entity.Student;
import lk.ijse.HostelManagementSystem.repository.DAOFactory;
import lk.ijse.HostelManagementSystem.repository.custom.StudentDAO;

import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO {
    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.STUDENT);

    @Override
    public List<StudentDTO> getAllStudents() throws Exception {
        List<Student> all = studentDAO.findAll();
        List<StudentDTO> allStudents = new ArrayList<>();
        for (Student student:all) {
            allStudents.add(new StudentDTO(student.getStudent_id(),student.getName(),student.getAddress(),student.getContact_no(),student.getDob(),student.getGender()));
        }
        return allStudents;
    }

    @Override
    public boolean addStudent(StudentDTO studentDTO) throws Exception {
        return  studentDAO.add(new Student(studentDTO.getStudent_id(),studentDTO.getName(),studentDTO.getAddress(),studentDTO.getContact_no(),studentDTO.getDob(),studentDTO.getGender()));
    }

    @Override
    public boolean updateStudent(StudentDTO studentDTO) throws Exception {
       return studentDAO.update(new Student(studentDTO.getStudent_id(),studentDTO.getName(),studentDTO.getAddress(),studentDTO.getContact_no(),studentDTO.getDob(),studentDTO.getGender()));
    }

    @Override
    public boolean deleteStudent(String id) throws Exception {
      return   studentDAO.delete(id);
    }

    @Override
    public boolean studentExist(String id)throws Exception  {
       return studentDAO.exist(id);

    }
}
