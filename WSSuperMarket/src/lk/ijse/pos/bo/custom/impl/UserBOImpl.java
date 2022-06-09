package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.UserBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.UserDAO;
import lk.ijse.pos.dto.UserDTO;
import lk.ijse.pos.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {
    private final UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException {
        ArrayList<UserDTO> allCustomers = new ArrayList<>();
        ArrayList<User> all = userDAO.getAll();
        for (User dto : all) {
            allCustomers.add(new UserDTO(dto.getUserId(),dto.getName(),dto.getRole(),dto.getTelNo(),dto.getEmail(),dto.getUserName(),dto.getPassword()));
        }
        return allCustomers;
    }

    @Override
    public String generateUserNewID() throws SQLException, ClassNotFoundException {
       return userDAO.generateNewID();
    }

    @Override
    public boolean existUser(String id) throws SQLException, ClassNotFoundException {
        return userDAO.exist(id);
    }

    @Override
    public boolean insertUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        return userDAO.insert(new User(dto.getUserId(),dto.getName(),dto.getRole(),dto.getTelNo(),dto.getEmail(),dto.getUserName(),dto.getPassword()));
    }

    @Override
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
        return userDAO.delete(id);
    }

    @Override
    public boolean updateUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        return userDAO.update(new User(dto.getUserId(),dto.getName(),dto.getRole(),dto.getTelNo(),dto.getEmail(),dto.getUserName(),dto.getPassword()));
    }

}
