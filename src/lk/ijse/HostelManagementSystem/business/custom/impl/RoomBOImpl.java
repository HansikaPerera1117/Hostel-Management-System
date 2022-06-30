package lk.ijse.HostelManagementSystem.business.custom.impl;

import lk.ijse.HostelManagementSystem.business.custom.RoomBO;
import lk.ijse.HostelManagementSystem.dto.RoomDTO;
import lk.ijse.HostelManagementSystem.entity.Room;
import lk.ijse.HostelManagementSystem.repository.DAOFactory;
import lk.ijse.HostelManagementSystem.repository.custom.RoomDAO;

import java.util.ArrayList;
import java.util.List;

public class RoomBOImpl implements RoomBO {

    private final RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ROOM);

    @Override
    public List<RoomDTO> getAllRooms() throws Exception {
        List<Room> all = roomDAO.findAll();
        List<RoomDTO> allRooms = new ArrayList<>();
        for (Room room:all) {
            allRooms.add(new RoomDTO(room.getRoom_type_id(),room.getType(),room.getKey_money(),room.getQty(),room.getAvailableRoomQty()));
        }
        return allRooms;
    }

    @Override
    public boolean addRoom(RoomDTO roomDTO) throws Exception {
       return roomDAO.add(new Room(roomDTO.getRoom_type_id(),roomDTO.getType(),roomDTO.getKey_money(),roomDTO.getQty(),roomDTO.getAvailableRoomQty()));
    }

    @Override
    public boolean updateRoom(RoomDTO roomDTO) throws Exception {
        return roomDAO.update(new Room(roomDTO.getRoom_type_id(),roomDTO.getType(),roomDTO.getKey_money(),roomDTO.getQty(),roomDTO.getAvailableRoomQty()));
    }

    @Override
    public boolean deleteRoom(String id) throws Exception {
        return roomDAO.delete(id);
    }

    @Override
    public boolean roomExist(String id) throws Exception {
        return roomDAO.exist(id);
    }
}
