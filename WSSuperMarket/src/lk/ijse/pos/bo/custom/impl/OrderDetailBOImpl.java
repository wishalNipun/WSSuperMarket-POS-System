package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.dao.DAOFactory;

import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.dao.custom.OrderDAO;
import lk.ijse.pos.dao.custom.QueryDAO;

import lk.ijse.pos.bo.custom.OrderDetailBO;
import lk.ijse.pos.dto.CustomDTO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.entity.CustomEntity;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.entity.Order;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailBOImpl implements OrderDetailBO {
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);

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
    public CustomerDTO searchCustomerDetail(String id) throws SQLException, ClassNotFoundException {
        Customer search = customerDAO.search(id);
        return new CustomerDTO(search.getId(),search.getTitle(),search.getName(),search.getAddress(),search.getCity(),search.getProvince(),search.getPostalCode());
    }

    @Override
    public ArrayList<CustomDTO> SearchOrderDetailsUsingOrderId(String id) throws SQLException, ClassNotFoundException {
        ArrayList<CustomEntity> customEntities = queryDAO.SearchOrderDetailsUsingOrderId(id);
        ArrayList<CustomDTO> orders = new ArrayList<>();
        for (CustomEntity c:customEntities
             ) {
            orders.add(
                    new CustomDTO(c.getCode(),c.getDescription(),c.getPackSize(),c.getUnitPrice(),c.getQty(),c.getDiscount(),c.getPrice())
            );

        }
        return orders;

    }

    @Override
    public ArrayList<OrderDTO> SearchCustomerOrders(String id) throws SQLException, ClassNotFoundException {
       ArrayList<Order> orders = orderDAO.SearchCustomerOrders(id);
       ArrayList<OrderDTO>allOrders = new ArrayList<>();
        for (Order o:orders
             ) {
            allOrders.add(
                    new OrderDTO(o.getOrderId(),o.getOrderDate(),o.getOrderTime(),o.getCost())
            );

        }
        return allOrders;
    }

}
