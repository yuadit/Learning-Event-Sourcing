package id.yuadit.learningeventsourcing.event;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
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

    public OrderEventType getEventType() {
        return eventType;
    }

    public boolean isPaid() {
        return paid;
    }
}
