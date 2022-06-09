package lk.ijse.pos.dao.custom;

import lk.ijse.pos.dao.SuperDAO;
import lk.ijse.pos.entity.CustomEntity;



import java.sql.SQLException;
import java.util.ArrayList;

public interface QueryDAO extends SuperDAO {
    ArrayList<CustomEntity> SearchOrderDetailsUsingOrderId(String id) throws SQLException, ClassNotFoundException;
}
