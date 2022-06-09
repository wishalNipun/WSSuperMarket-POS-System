package lk.ijse.pos.view.tm;

import java.time.LocalDate;
import java.time.LocalTime;

public class OrderTM {
    private String orderId;
    private LocalDate orderDate;
    private LocalTime orderTime;
    private double cost;

    public OrderTM() {
    }

    public OrderTM(String orderId, LocalDate orderDate, LocalTime orderTime, double cost) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.cost = cost;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalTime orderTime) {
        this.orderTime = orderTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "OrderTM{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", orderTime=" + orderTime +
                ", cost=" + cost +
                '}';
    }
}
