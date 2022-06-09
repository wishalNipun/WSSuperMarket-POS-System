package lk.ijse.pos.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Order {
    private String orderId;
    private String customerId;
    private LocalDate orderDate;
    private LocalTime orderTime;
    private double cost;

    public Order(LocalDate orderDate, double cost) {
        this.orderDate = orderDate;
        this.cost = cost;
    }

    public Order(String orderId, LocalDate orderDate, LocalTime orderTime, double cost) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.cost = cost;
    }

    public Order() {
    }

    public Order(String orderId, String customerId, LocalDate orderDate, LocalTime orderTime, double cost) {
        this.orderId = orderId;
        this.customerId = customerId;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderDate=" + orderDate +
                ", orderTime=" + orderTime +
                ", cost=" + cost +
                '}';
    }
}
