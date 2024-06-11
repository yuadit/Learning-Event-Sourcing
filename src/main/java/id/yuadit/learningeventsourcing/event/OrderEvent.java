package id.yuadit.learningeventsourcing.event;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class OrderEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    private Long orderId;
    private String eventType;
    private String eventData;
    private LocalDateTime timestamp;

    // Constructors, getters, setters
    public OrderEvent() {}

    public OrderEvent(Long orderId, String eventType, String eventData) {
        this.orderId = orderId;
        this.eventType = eventType;
        this.eventData = eventData;
        this.timestamp = LocalDateTime.now();
    }

    public Long getEventId() {
        return eventId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventData() {
        return eventData;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
