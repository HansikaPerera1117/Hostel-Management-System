package lk.ijse.HostelManagementSystem.repository.custom.impl;

import lk.ijse.HostelManagementSystem.entity.Reservation;
import lk.ijse.HostelManagementSystem.repository.custom.ReservationDAO;
import lk.ijse.HostelManagementSystem.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {
    @Override
    public boolean add(Reservation entity) throws Exception {
        return false;
    }

    @Override
    public boolean update(Reservation entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public boolean exist(String id) throws Exception {
        return false;
    }

    @Override
    public List<Reservation> find(String id) throws Exception {
        return null;
    }

    @Override
    public List<Reservation> findAll() throws Exception {
        return null;
    }

}
