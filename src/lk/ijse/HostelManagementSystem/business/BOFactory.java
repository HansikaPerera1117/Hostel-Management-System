package lk.ijse.HostelManagementSystem.business;

import lk.ijse.HostelManagementSystem.business.custom.impl.*;
import lk.ijse.HostelManagementSystem.entity.Student;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBoFactory(){
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        STUDENT, ROOM, RESERVATION,USER,SEARCHREGISTRATION
    }

    public SuperBO getBO(BOTypes types){
        switch (types){
            case STUDENT:
                return new StudentBOImpl();
            case ROOM:
                return new RoomBOImpl();
            case RESERVATION:
                return new ReservationBOImpl();
            case USER:
                return new UserBOImpl();
            case SEARCHREGISTRATION:
                return new SearchRegistrationBOImpl();
            default:
                return null;
        }
    }
}
