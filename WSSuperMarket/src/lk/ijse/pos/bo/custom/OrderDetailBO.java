package lk.ijse.pos.bo.custom;

import lk.ijse.pos.bo.SuperBO;

import lk.ijse.pos.dto.CustomDTO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.OrderDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailBO extends SuperBO {
    boolean deleteOrder(String code)throws SQLException,ClassNotFoundException;

    ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;

    CustomerDTO searchCustomerDetail(String id) throws SQLException, ClassNotFoundException;

    ArrayList<CustomDTO> SearchOrderDetailsUsingOrderId(String id) throws SQLException, ClassNotFoundException;

    ArrayList<OrderDTO> SearchCustomerOrders(String id) throws SQLException, ClassNotFoundException;


}
