package lk.ijse.HostelManagementSystem.business;

import lk.ijse.HostelManagementSystem.business.custom.impl.ReservationBOImpl;
import lk.ijse.HostelManagementSystem.business.custom.impl.RoomBOImpl;
import lk.ijse.HostelManagementSystem.business.custom.impl.StudentBOImpl;
import lk.ijse.HostelManagementSystem.business.custom.impl.RemainKeyMoneyStudentsBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBoFactory(){
        return (boFactory == null) ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        STUDENT, ROOM, RESERVATION,REMAINKEYMONEYSTUDENT
    }

    public SuperBO getBO(BOTypes types){
        switch (types){
            case STUDENT:
                return new StudentBOImpl();
            case ROOM:
                return new RoomBOImpl();
            case RESERVATION:
                return new ReservationBOImpl();
            case REMAINKEYMONEYSTUDENT:
                return new RemainKeyMoneyStudentsBOImpl();
            default:
                return null;
        }
    }
}
