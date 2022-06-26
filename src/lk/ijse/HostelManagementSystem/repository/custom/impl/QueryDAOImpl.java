package lk.ijse.HostelManagementSystem.repository.custom.impl;

import lk.ijse.HostelManagementSystem.entity.CustomEntity;
import lk.ijse.HostelManagementSystem.repository.custom.QueryDAO;
import lk.ijse.HostelManagementSystem.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public List<CustomEntity> remainKeyMoneyStudents() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT s.student_id,s.name,s.contact_no,r.res_id,ro.room_type_id,r.date,r.status FROM Student s INNER JOIN Reservation r on s.student_id = r.student INNER JOIN Room ro on ro.room_type_id = r.room WHERE r.status LIKE '%is payable'";
        List<CustomEntity> remainKeyMoneyStudentsList = session.createQuery(hql).list();

        transaction.commit();
        session.close();
        return remainKeyMoneyStudentsList;
    }
}
