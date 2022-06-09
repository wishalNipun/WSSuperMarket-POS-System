package lk.ijse.pos.bo.custom;

import lk.ijse.pos.bo.SuperBO;
import lk.ijse.pos.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {
    ArrayList<UserDTO> getAllUsers() throws SQLException, ClassNotFoundException;

    String generateUserNewID() throws SQLException, ClassNotFoundException;

    boolean existUser(String id) throws SQLException, ClassNotFoundException;

    boolean insertUser(UserDTO dto) throws SQLException, ClassNotFoundException;

    boolean deleteUser(String id) throws SQLException, ClassNotFoundException;

    boolean updateUser(UserDTO dto) throws SQLException, ClassNotFoundException;

}
