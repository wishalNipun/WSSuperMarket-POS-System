package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.ItemBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        ArrayList<Item> all = itemDAO.getAll();
        for (Item i : all) {
            allItems.add(new ItemDTO(i.getCode(), i.getDescription(),i.getPackSize(), i.getUnitPrice(), i.getQtyOnHand()));
        }
        return allItems;
    }

    @Override
    public boolean ItemInsert(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.insert(new Item(dto.getCode(), dto.getDescription(),dto.getPackSize(), dto.getUnitPrice(),dto.getQtyOnHand()));

    }

    @Override
    public boolean ItemDelete(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(code);
    }

    @Override
    public boolean ItemUpdate(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(dto.getCode(), dto.getDescription(),dto.getPackSize(), dto.getUnitPrice(),dto.getQtyOnHand()));
    }

    @Override
    public boolean ItemExist(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(code);
    }

    @Override
    public String generateItemNewID() throws SQLException, ClassNotFoundException {
        return itemDAO.generateNewID();
    }
}
