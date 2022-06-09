package lk.ijse.pos.bo;

import lk.ijse.pos.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){

    }
    public static BOFactory getBOFactory(){
        if (boFactory==null){
            return new BOFactory();
        }
        return boFactory;
    }

    public enum BOTypes {
        CUSTOMER, ITEM,ITEM_MOVEMENT,LOG,ORDER_DETAIL, PURCHASE_ORDER,REPORT,USER
    }


    public SuperBO getBO(BOFactory.BOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case ITEM_MOVEMENT:
                return new ItemMovementBOImpl();
            case LOG:
                return new LogBOImpl();
            case ORDER_DETAIL:
                return new OrderDetailBOImpl();
            case PURCHASE_ORDER:
                return  new PurchaseOrderBOImpl();
            case REPORT:
                return new ReportBOImpl();
            case USER:
                return new UserBOImpl();
            default:
                return null;
        }
    }
}
