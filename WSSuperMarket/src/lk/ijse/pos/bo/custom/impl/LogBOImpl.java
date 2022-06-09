package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.LogBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.UserDAO;
import lk.ijse.pos.dto.UserDTO;
import lk.ijse.pos.entity.User;

import java.sql.SQLException;

public class LogBOImpl implements LogBO {
    private final UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);
    @Override
    public UserDTO getUser(String userName, String password) throws SQLException, ClassNotFoundException {
        User user= userDAO.getUser(userName,password);

        return new UserDTO(user.getUserId(),user.getName(),user.getRole(),user.getTelNo(),user.getEmail(),user.getUserName(),user.getPassword());
    }
}
