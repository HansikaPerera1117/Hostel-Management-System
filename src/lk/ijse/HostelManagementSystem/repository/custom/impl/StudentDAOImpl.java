package lk.ijse.HostelManagementSystem.repository.custom.impl;

import lk.ijse.HostelManagementSystem.entity.Student;
import lk.ijse.HostelManagementSystem.repository.custom.StudentDAO;
import lk.ijse.HostelManagementSystem.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public boolean add(Student entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Student entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Student student = session.load(Student.class, id);
        session.delete(student);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean exist(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT student_id FROM Student WHERE student_id = : s_id ";
        Query query = session.createQuery(hql);
        query.setParameter("s_id",id);
        List<String> student_id = query.list();

        transaction.commit();
        session.close();

        return (student_id.size()>0) ? true : false;
    }

    @Override
    public List<Student> find(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Student WHERE student_id = : s_id ";
        Query query = session.createQuery(hql);
        query.setParameter("s_id",id);
        List<Student> student_id = query.list();

        transaction.commit();
        session.close();
        return student_id;
    }

    @Override
    public List<Student> findAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql ="FROM Student";
        Query query = session.createQuery(hql);
        List<Student> studentList = query.list();

        transaction.commit();
        session.close();
        return studentList;
    }
}
