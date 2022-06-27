package lk.ijse.HostelManagementSystem.business.custom.impl;

import lk.ijse.HostelManagementSystem.business.custom.UserBO;
import lk.ijse.HostelManagementSystem.dto.UserDTO;
import lk.ijse.HostelManagementSystem.entity.User;
import lk.ijse.HostelManagementSystem.repository.DAOFactory;
import lk.ijse.HostelManagementSystem.repository.custom.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class UserBOImpl implements UserBO {
    private final UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.USER);

    @Override
    public List<UserDTO> getAllUsers() throws Exception {
        List<User> all = userDAO.findAll();
        List<UserDTO> allUsers = new ArrayList<>();
        for (User user:all) {
            allUsers.add(new UserDTO(user.getUserName(),user.getPassWord()));
        }
        return allUsers;
    }

    @Override
    public boolean registerUser(UserDTO userDTO) throws Exception {
        return userDAO.add(new User(userDTO.getUserName(),userDTO.getPassWord()));
    }

    @Override
    public boolean updateUserDetails(UserDTO userDTO) throws Exception {
        return userDAO.update(new User(userDTO.getUserName(),userDTO.getPassWord()));
    }

    @Override
    public boolean deleteUserAccount(String username) throws Exception {
        return userDAO.delete(username);
    }
}
