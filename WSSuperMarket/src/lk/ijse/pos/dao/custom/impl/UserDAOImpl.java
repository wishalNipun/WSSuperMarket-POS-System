package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.CrudUtil;
import lk.ijse.pos.dao.custom.UserDAO;
import lk.ijse.pos.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {
    @Override
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM `User`");
        ArrayList<User> allUsers = new ArrayList<>();
        while (rst.next()) {
            allUsers.add(new User(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5),rst.getString(6),rst.getString(7)));
        }
        return allUsers;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT id FROM `User`ORDER BY id DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("id");
            int newItemId = Integer.parseInt(id.replace("U00-", "")) + 1;
            return String.format("U00-%03d", newItemId);
        } else {
            return "U00-001";
        }
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT id FROM `User` WHERE id=?", id).next();
    }

    @Override
    public boolean insert(User dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO `User` VALUES (?,?,?,?,?,?,?)",dto.getUserId(),dto.getName(),dto.getRole(),dto.getTelNo(),dto.getEmail(),dto.getUserName(),dto.getPassword());

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("Delete From `User` Where id =?",id);

    }

    @Override
    public boolean update(User entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("Update `User` SET name=? , role=? ,telNo=? , email=? ,userName=? ,password=?  WHERE id=?", entity.getName(), entity.getRole(), entity.getTelNo(), entity.getEmail(), entity.getUserName(), entity.getPassword(), entity.getUserId());
    }

    @Override
    public User search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
    public User getUser(String userName,String password) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM User WHERE UserName=? and Password=?",userName,password);
        if (rst.next()) {
            return new User(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7)
            );

        } else {
            return null;
        }
    }
}
