package lk.ijse.pos.entity;

public class OrderDetail {
    private String orderId;
    private String itemCode;
    private int qty;
    private double price;
    private double discount;

    public OrderDetail() {
    }

    public OrderDetail(String itemCode, int qty) {
        this.itemCode = itemCode;
        this.qty = qty;
    }

    public OrderDetail(String orderId, String itemCode, int qty, double price, double discount) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.price = price;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}
