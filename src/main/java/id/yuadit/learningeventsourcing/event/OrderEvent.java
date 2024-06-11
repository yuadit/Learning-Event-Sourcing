package id.yuadit.learningeventsourcing.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
public class OrderEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID eventId;
    private LocalDateTime timestamp;
    private Long orderId;
    private Long customerId;
    private String items;
    private OrderEventType eventType;
    private Boolean paid;

    public OrderEvent(){
    }

    public OrderEvent(Long orderId, Long customerId, String items, OrderEventType eventType, boolean paid) {
        this.eventId = UUID.randomUUID();
        this.timestamp = LocalDateTime.now();
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
        this.eventType = eventType;
        this.paid = paid;
    }

    // Additional constructor without `paid` for non-payment events
    public OrderEvent(Long orderId, Long customerId, String items, OrderEventType eventType) {
        this(orderId, customerId, items, eventType, false);
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public UUID getEventId() {
        return eventId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getItems() {
        return items;
    }

    public List<String> getItemsAsList() {
        List<String> itemsAsList = new ArrayList<>();
        try {
            itemsAsList.addAll(Arrays.stream(items.split(",")).toList());
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return itemsAsList;
    }

    public OrderEventType getEventType() {
        return eventType;
    }

    public boolean isPaid() {
        return paid;
    }
}
