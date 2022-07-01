package lk.ijse.HostelManagementSystem.repository.custom.impl;

import lk.ijse.HostelManagementSystem.entity.Reservation;
import lk.ijse.HostelManagementSystem.repository.custom.ReservationDAO;
import lk.ijse.HostelManagementSystem.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {
    @Override
    public boolean add(Reservation entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Reservation entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String res_id = entity.getRes_id();
        String status = entity.getStatus();
        String hql = "UPDATE Reservation SET status = :reservaton_status WHERE res_id = :resID";
        Query query = session.createQuery(hql);
        query.setParameter("reservaton_status",status);
        query.setParameter("resID",res_id);
        int rowCount = query.executeUpdate();

        transaction.commit();
        session.close();
        return (rowCount > 0 ? true : false);
    }

    @Override
    public boolean delete(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Reservation reservation = session.load(Reservation.class, id);
        session.delete(reservation);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean exist(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT res_id FROM Reservation WHERE res_id = : reservation_id ";
        Query query = session.createQuery(hql);
        query.setParameter("reservation_id",id);
        List<String> reservation_Id = query.list();

        transaction.commit();
        session.close();

        return (reservation_Id.size()>0) ? true : false;
    }

    @Override
    public List<Reservation> find(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Reservation WHERE res_id = : reservation_id ";
        Query query = session.createQuery(hql);
        query.setParameter("reservation_id",id);
        List<Reservation> reservation_Id = query.list();

        transaction.commit();
        session.close();
        return reservation_Id;
    }

    @Override
    public List<Reservation> findAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql ="FROM Reservation";
        Query query = session.createQuery(hql);
        List<Reservation> reservationList = query.list();

        transaction.commit();
        session.close();
        return reservationList;
    }

    @Override
    public List<Reservation> getAllReservationsAccordingToStudent(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Reservation WHERE student.student_id = : s_id ";
        Query query = session.createQuery(hql);
        query.setParameter("s_id",id);
        List<Reservation> reservation_Id = query.list();

        transaction.commit();
        session.close();
        return reservation_Id;
    }

    @Override
    public List<Reservation> getAllReservationsAccordingToRoom(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Reservation WHERE room.room_type_id = : room_id ";
        Query query = session.createQuery(hql);
        query.setParameter("room_id",id);
        List<Reservation> reservation_Id = query.list();

        transaction.commit();
        session.close();
        return reservation_Id;
    }

    @Override
    public String generateNewReservationId() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<String> list = session.createQuery("SELECT res_id FROM Reservation ORDER BY res_id DESC").setMaxResults(1).list();

        transaction.commit();
        session.close();

        return list.size()>0? String.format("R00-%03d", (Integer.parseInt(list.get(0).replace("R00-", "")) + 1)) : "R00-001";
    }

    @Override
    public List<Reservation> remainKeyMoneyStudents() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Reservation WHERE status LIKE '%is payable'";
        List<Reservation> remainKeyMoneyStudentsList = session.createQuery(hql).list();

        transaction.commit();
        session.close();
        return remainKeyMoneyStudentsList;
    }
}
