package lk.ijse.HostelManagementSystem.repository.custom;

import lk.ijse.HostelManagementSystem.entity.CustomEntity;
import lk.ijse.HostelManagementSystem.entity.SuperEntity;
import lk.ijse.HostelManagementSystem.repository.SuperDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface QueryDAO extends SuperDAO{
    List<CustomEntity> remainKeyMoneyStudents()throws Exception;
}
