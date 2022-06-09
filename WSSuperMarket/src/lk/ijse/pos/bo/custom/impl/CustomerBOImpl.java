package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.CustomerBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for (Customer customer : all) {
            allCustomers.add(new CustomerDTO(customer.getId(),customer.getTitle(), customer.getName(), customer.getAddress(),customer.getCity(),customer.getProvince(),customer.getPostalCode()));
        }
        return allCustomers;
    }

    @Override
    public boolean customerExist(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public boolean insertCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.insert(new Customer(dto.getId(),dto.getTitle(), dto.getName(), dto.getAddress(),dto.getCity(),dto.getProvince(),dto.getPostalCode()));

    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getId(),dto.getTitle(), dto.getName(), dto.getAddress(),dto.getCity(),dto.getProvince(),dto.getPostalCode()));
    }

    @Override
    public String generateCustomerNewID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID();
    }
}
