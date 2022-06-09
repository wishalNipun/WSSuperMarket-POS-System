package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.PurchaseOrderBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.dao.custom.OrderDAO;
import lk.ijse.pos.dao.custom.OrderDetailDAO;
import lk.ijse.pos.db.DBConnection;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.dto.OrderDetailDTO;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.entity.Item;
import lk.ijse.pos.entity.Order;
import lk.ijse.pos.entity.OrderDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    @Override
    public boolean purchaseOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        if (orderDAO.exist(dto.getOrderId())) {

        }

        connection.setAutoCommit(false);

        Boolean save = orderDAO.insert(new Order(dto.getOrderId(),dto.getCustomerId(),dto.getOrderDate(),dto.getOrderTime(),dto.getCost()));
        if (!save) {

            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (OrderDetailDTO detailDTO : dto.getItems()) {

            Boolean save1 = orderDetailDAO.insert(new OrderDetail(dto.getOrderId(), detailDTO.getItemCode(), detailDTO.getOrderQty(), detailDTO.getUnitPrice(), detailDTO.getDiscount()));

            if (!save1) {
                System.out.println("no");
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            //Search & Update Item

            ItemDTO item = searchItem(detailDTO.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand() - detailDTO.getOrderQty());

            Boolean update = itemDAO.update(new Item(item.getCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(), item.getQtyOnHand()));
            if (!update) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        connection.commit();
        connection.setAutoCommit(true);
        return true;

    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer search = customerDAO.search(id);
        return  new CustomerDTO(search.getId(),search.getTitle(),search.getName(),search.getAddress(),search.getCity(),search.getProvince(),search.getPostalCode());
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item search = itemDAO.search(code);
        return new ItemDTO(search.getCode(),search.getDescription(),search.getPackSize(),search.getUnitPrice(),search.getQtyOnHand());
    }

    @Override
    public boolean checkItemIsAvailable(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }

    @Override
    public boolean checkCustomerIsAvailable(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewID();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        for (Customer c:all
        ) {
            allCustomers.add(new CustomerDTO(c.getId(),c.getTitle(), c.getName(),c.getAddress(),c.getCity(),c.getProvince(),c.getPostalCode()));
        }
        return allCustomers;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        for (Item i : all) {
            allItems.add(new ItemDTO(i.getCode(),i.getDescription(),i.getPackSize(),i.getUnitPrice(), i.getQtyOnHand()));
        }
        return allItems;
    }
}
