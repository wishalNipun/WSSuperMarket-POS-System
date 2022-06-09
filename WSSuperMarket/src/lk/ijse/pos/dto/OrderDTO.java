package lk.ijse.pos.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class OrderDTO {
    private String orderId;
    private String customerId;
    private LocalDate orderDate;
    private LocalTime orderTime;
    private double cost;
    private List<OrderDetailDTO> items;

    public OrderDTO() {
    }
    public OrderDTO(LocalDate orderDate, double cost) {
        this.orderDate = orderDate;
        this.cost = cost;
    }

    public OrderDTO(String orderId, LocalDate orderDate, LocalTime orderTime, double cost) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.cost = cost;
    }

    public OrderDTO(String orderId, String customerId, LocalDate orderDate, LocalTime orderTime, double cost, List<OrderDetailDTO> items) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.cost = cost;
        this.items = items;
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

    public List<OrderDetailDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderDetailDTO> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderDate=" + orderDate +
                ", orderTime=" + orderTime +
                ", cost=" + cost +
                ", items=" + items +
                '}';
    }
}
