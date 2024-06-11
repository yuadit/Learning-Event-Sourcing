package id.yuadit.learningeventsourcing.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.yuadit.learningeventsourcing.event.OrderEvent;

import java.io.IOException;

public class Order {
    private Long orderId;
    private Long customerId;
    private String items;
    private boolean deleted;

    // Constructors, getters, setters
    public Order(Long orderId) {
        this.orderId = orderId;
    }

    public void apply(OrderEvent event) {
        switch (event.getEventType()) {
            case ORDER_CREATED:
            case ORDER_UPDATED:
                this.customerId = event.getCustomerId();
                this.items = event.getItems();
                break;
            case ORDER_DELETED:
                this.deleted = true;
                break;
        }
    }

    // Getters and setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}