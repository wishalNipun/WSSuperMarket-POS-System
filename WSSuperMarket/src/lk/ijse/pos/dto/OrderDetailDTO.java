package lk.ijse.pos.dto;

public class OrderDetailDTO {
    private String orderId;
    private String itemCode;
    private int orderQty;
    private double unitPrice;
    private double discount;

    public OrderDetailDTO(String itemCode, int orderQty) {
        this.itemCode = itemCode;
        this.orderQty = orderQty;
    }

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(String orderId, String itemCode, int orderQty, double unitPrice, double discount) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.orderQty = orderQty;
        this.unitPrice = unitPrice;
        this.discount = discount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", orderQty=" + orderQty +
                ", unitPrice=" + unitPrice +
                ", discount=" + discount +
                '}';
    }
}
