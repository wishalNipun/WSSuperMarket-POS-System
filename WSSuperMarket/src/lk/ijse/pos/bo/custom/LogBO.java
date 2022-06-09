package lk.ijse.pos.bo.custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.UserDTO;

import java.sql.SQLException;

public interface LogBO extends SuperBO {
    UserDTO getUser(String userName, String password) throws SQLException, ClassNotFoundException;

}
