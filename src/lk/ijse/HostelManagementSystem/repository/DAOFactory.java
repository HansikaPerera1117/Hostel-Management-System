package lk.ijse.HostelManagementSystem.repository;

import lk.ijse.HostelManagementSystem.repository.custom.impl.QueryDAOImpl;
import lk.ijse.HostelManagementSystem.repository.custom.impl.ReservationDAOImpl;
import lk.ijse.HostelManagementSystem.repository.custom.impl.RoomDAOImpl;
import lk.ijse.HostelManagementSystem.repository.custom.impl.StudentDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static DAOFactory getDaoFactory(){
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOType{
        STUDENT, ROOM, RESERVATION,REMAINKEYMONEYSTUDENT
    }

    public SuperDAO getDAO(DAOType type){
        switch (type){
            case STUDENT:
                return new StudentDAOImpl();
            case ROOM:
                return new RoomDAOImpl();
            case RESERVATION:
                return new ReservationDAOImpl();
            case REMAINKEYMONEYSTUDENT:
                return  new QueryDAOImpl();
            default:
                return null;
        }
    }
}
