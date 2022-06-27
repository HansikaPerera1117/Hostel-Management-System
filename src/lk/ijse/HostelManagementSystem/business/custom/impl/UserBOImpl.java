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
            allUsers.add(new UserDTO(user.getUserName(),user.getPassWord(),user.getEmail()));
        }
        return allUsers;
    }

    @Override
    public List<UserDTO> searchUser(String username) throws Exception {
        List<User> userList = userDAO.find(username);
        List<UserDTO> userDetails = new ArrayList<>();
        for (User user:userList) {
            userDetails.add(new UserDTO(user.getUserName(),user.getPassWord(),user.getEmail()));
        }
        return userDetails;
    }


    @Override
    public boolean registerUser(UserDTO userDTO) throws Exception {
        return userDAO.add(new User(userDTO.getUserName(),userDTO.getPassWord(),userDTO.getEmail()));
    }

    @Override
    public boolean updateUserDetails(UserDTO userDTO) throws Exception {
        return userDAO.update(new User(userDTO.getUserName(),userDTO.getPassWord(),userDTO.getEmail()));
    }

    @Override
    public boolean deleteUserAccount(String username) throws Exception {
        return userDAO.delete(username);
    }

    @Override
    public boolean emailExist(String email) throws Exception {
       return userDAO.exist(email);
    }
}
