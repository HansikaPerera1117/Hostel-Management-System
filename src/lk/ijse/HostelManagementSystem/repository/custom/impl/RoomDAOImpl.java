package lk.ijse.HostelManagementSystem.repository.custom.impl;

import lk.ijse.HostelManagementSystem.entity.Room;
import lk.ijse.HostelManagementSystem.entity.Student;
import lk.ijse.HostelManagementSystem.repository.custom.RoomDAO;
import lk.ijse.HostelManagementSystem.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class RoomDAOImpl implements RoomDAO {
    @Override
    public boolean add(Room entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Room entity) throws Exception {
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

        Room room = session.load(Room.class, id);
        session.delete(room);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean exist(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT room_type_id FROM Room WHERE room_type_id = : room_id ";
        Query query = session.createQuery(hql);
        query.setParameter("room_id",id);
        List<String> room_list = query.list();

        transaction.commit();
        session.close();

        return (room_list.size()>0) ? true : false;
    }

    @Override
    public List<Room> find(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql = "FROM Room WHERE room_type_id = : room_id ";
        Query query = session.createQuery(hql);
        query.setParameter("room_id",id);
        List<Room> roomList = query.list();

        transaction.commit();
        session.close();
        return roomList;
    }

    @Override
    public List<Room> findAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hql ="FROM Room ";
        Query query = session.createQuery(hql);
        List<Room> roomList = query.list();

        transaction.commit();
        session.close();
        return roomList;
    }
}
