package lk.ijse.HostelManagementSystem.business.custom.impl;

import lk.ijse.HostelManagementSystem.business.custom.RemainKeyMoneyStudentsBO;
import lk.ijse.HostelManagementSystem.dto.CustomDTO;
import lk.ijse.HostelManagementSystem.entity.CustomEntity;
import lk.ijse.HostelManagementSystem.repository.DAOFactory;
import lk.ijse.HostelManagementSystem.repository.custom.QueryDAO;

import java.util.ArrayList;
import java.util.List;

public class RemainKeyMoneyStudentsBOImpl implements RemainKeyMoneyStudentsBO {

    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.REMAINKEYMONEYSTUDENT);

    @Override
    public List<CustomDTO> getAllRemainingKeyMoneyStudents() throws Exception {
        List<CustomEntity> customEntityList = queryDAO.remainKeyMoneyStudents();
        List<CustomDTO> list = new ArrayList<>();
        for (CustomEntity entity : customEntityList) {
            list.add(new CustomDTO(entity.getStudent_id(),entity.getName(),entity.getContact_no(),entity.getRes_id(),entity.getRoom_type_id(),entity.getDate(),entity.getStatus()));
        }
        return list;

     /*   List<CustomEntity> remainKeyMoneyStudents = queryDAO.remainKeyMoneyStudents();
        List<CustomDTO> allRemainKeyMoneyStudents = new ArrayList<>();
        for (CustomEntity entity:remainKeyMoneyStudents) {
            allRemainKeyMoneyStudents.add(new CustomDTO(entity.getStudent_id(),entity.getName(),entity.getContact_no(),entity.getRes_id(),entity.getRoom_type_id(),entity.getDate(),entity.getStatus()));
        }
        return allRemainKeyMoneyStudents;*/
    }
}
