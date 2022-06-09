package lk.ijse.pos.dao;

import lk.ijse.pos.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){

    }
    public static DAOFactory getDaoFactory(){
        if (daoFactory==null){
            return new DAOFactory();
        }
        return daoFactory;
    }

    public enum DAOTypes {
        CUSTOMER, ITEM, ORDER, ORDERDETAILS, QUERYDAO,USER
    }


    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerDAOImpl(); //SuperDAO superDAO=new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl(); //SuperDAO superDAO=new ItemDAOImpl();
            case ORDER:
                return  new OrderDAOImpl(); //SuperDAO superDAO = new OrderDAOImpl();
            case ORDERDETAILS:
                return new OrderDetailDAOImpl(); //SuperDAO superDAO = new OrderDetailsDAOImpl();
            case QUERYDAO:
                return new QueryDAOImpl(); //SuperDAO superDAO = new QueryDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }
    }
}
