package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.dao.DAOFactory;

import lk.ijse.pos.dao.custom.*;

import lk.ijse.pos.bo.custom.OrderDetailBO;
import lk.ijse.pos.dao.custom.impl.ItemDAOImpl;
import lk.ijse.pos.dao.custom.impl.OrderDetailDAOImpl;
import lk.ijse.pos.db.DBConnection;
import lk.ijse.pos.dto.CustomDTO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.entity.CustomEntity;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.entity.Item;
import lk.ijse.pos.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailBOImpl implements OrderDetailBO {
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final QueryDAO queryDAO = (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERYDAO);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    @Override
    public boolean deleteOrder(String code) throws SQLException, ClassNotFoundException {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            connection.setAutoCommit(false);
            String id = code;
            ArrayList<CustomEntity> customDTOS = queryDAO.SearchOrderDetailsUsingOrderId(code);


            for (CustomEntity c:customDTOS
            ) {

                int afterUpdateQty= c.getQty()+itemDAO.search(c.getCode()).getQtyOnHand();

                boolean isUpdateItem =itemDAO.updateItemQtyUsingItemCode(
                        new Item(c.getCode(),afterUpdateQty));
                if (!isUpdateItem){
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }

            }

            boolean isDeletedOrderDetail= orderDetailDAO.delete(id);

            if (!isDeletedOrderDetail){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            boolean isDeletedOrder= orderDAO.delete(id);
            if (!isDeletedOrder){
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

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
