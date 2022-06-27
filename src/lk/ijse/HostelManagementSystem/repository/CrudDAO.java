package lk.ijse.HostelManagementSystem.repository;

import lk.ijse.HostelManagementSystem.entity.SuperEntity;

import java.util.List;

public interface CrudDAO <Entity extends SuperEntity, ID> extends SuperDAO{
    boolean add(Entity entity) throws Exception;

    boolean update(Entity entity) throws Exception;

    boolean delete(ID id) throws Exception;

    boolean exist(ID id) throws Exception;

    List<Entity> find(ID id) throws Exception;

    List<Entity> findAll() throws Exception;
}
