package lk.ijse.HostelManagementSystem.repository;

import lk.ijse.HostelManagementSystem.entity.SuperEntity;

import java.util.List;

public interface SuperDAO<Entity extends SuperEntity, ID>{
     boolean add(Entity entity) throws Exception;

     boolean update(Entity entity) throws Exception;

     boolean delete(ID id) throws Exception;

     boolean exist(ID id) throws Exception;

     Entity find(ID id) throws Exception;

     List<Entity> findAll() throws Exception;
}
