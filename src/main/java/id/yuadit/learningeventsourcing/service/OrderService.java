package id.yuadit.learningeventsourcing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import id.yuadit.learningeventsourcing.event.OrderEvent;
import id.yuadit.learningeventsourcing.event.OrderEventType;
import id.yuadit.learningeventsourcing.model.Order;
import id.yuadit.learningeventsourcing.repository.OrderEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderEventRepository eventRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public Long createOrder(Long customerId, String items) {
        Long nextOrderId = getNextOrderId();
        OrderEvent event = new OrderEvent(nextOrderId, customerId, items, OrderEventType.ORDER_CREATED);
        eventRepository.save(event);
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("order-events", eventJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return nextOrderId;
    }

    public void updateOrder(Long orderId, Long customerId, String items) {
        OrderEvent event = new OrderEvent(orderId, customerId, items, OrderEventType.ORDER_UPDATED);
        eventRepository.save(event);
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("order-events", eventJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(Long orderId) {
        OrderEvent event = new OrderEvent(orderId, null, null, OrderEventType.ORDER_DELETED);
        eventRepository.save(event);
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("order-events", eventJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void markOrderAsPaid(Long orderId) {
        OrderEvent lastEvent = eventRepository.findLastEventByOrderId(orderId);
        OrderEvent event = new OrderEvent(orderId, lastEvent.getCustomerId(), lastEvent.getItems(), OrderEventType.INVOICE_PAID, true);
        eventRepository.save(event);
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("order-events", eventJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<OrderEvent> getOrderHistory(Long orderId) {
        return eventRepository.findByOrderId(orderId);
    }

    public Order rebuildOrderState(Long orderId) {
        List<OrderEvent> events = getOrderHistory(orderId);
        Order order = new Order(orderId);

        for (OrderEvent event : events) {
            order.apply(event);
        }
        return order;
    }

    private Long getNextOrderId() {
        Long maxOrderId = eventRepository.findMaxOrderId();
        return (maxOrderId == null) ? 1 : maxOrderId + 1;
    }
}
