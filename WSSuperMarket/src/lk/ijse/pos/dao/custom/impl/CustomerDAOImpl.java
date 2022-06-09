package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.CrudUtil;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomers = new ArrayList();
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer");
        while (rst.next()) {
            allCustomers.add(
                    new Customer(rst.getString(1),
                            rst.getString(2),
                            rst.getString(3),
                            rst.getString(4),
                            rst.getString(5),
                            rst.getString(6),
                            rst.getInt(7))
            );
        }
        return allCustomers;

    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT CustId FROM Customer ORDER BY CustId DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("CustId");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeQuery("SELECT CustId FROM Customer WHERE CustId=?", id).next();
    }

    @Override
    public boolean insert(Customer dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("INSERT INTO Customer VALUES(?,?,?,?,?,?,?)", dto.getId(), dto.getTitle(),
                dto.getName(), dto.getAddress(), dto.getCity(), dto.getProvince(), dto.getPostalCode());

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("DELETE FROM Customer WHERE CustId=?", id);
    }

    @Override
    public boolean update(Customer dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("UPDATE Customer SET CustTitle=?,CustName=?,CustAddress=?,City=?,Province=?, PostalCode=? WHERE CustId=?", dto.getTitle(), dto.getName(), dto.getAddress(), dto.getCity(), dto.getProvince(), dto.getPostalCode(), dto.getId());

    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM Customer WHERE CustId=?", id);
        rst.next();
        return new Customer(id,
                rst.getString(2),
                rst.getString(3),
                rst.getString(4),
                rst.getString(5),
                rst.getString(6),
                rst.getInt(7)
        );
    }
}
