package lk.ijse.pos.bo.custom;

import lk.ijse.pos.bo.SuperBO;

import lk.ijse.pos.dto.ItemDTO;


import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {

    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    boolean ItemInsert(ItemDTO dto) throws SQLException, ClassNotFoundException;

    boolean ItemDelete(String code) throws SQLException, ClassNotFoundException;

    boolean ItemUpdate(ItemDTO dto) throws SQLException, ClassNotFoundException;

    boolean ItemExist(String code) throws SQLException, ClassNotFoundException;

    String generateItemNewID() throws SQLException, ClassNotFoundException;

}
