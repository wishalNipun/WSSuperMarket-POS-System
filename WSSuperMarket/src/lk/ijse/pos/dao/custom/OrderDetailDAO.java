package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.CrudDAO;
import lk.ijse.pos.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailDAO  extends CrudDAO<OrderDetail,String> {
    ArrayList<OrderDetail> getAllSellItemsCountChart()throws SQLException,ClassNotFoundException;
}
