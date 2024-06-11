package id.yuadit.learningeventsourcing.service;

import id.yuadit.learningeventsourcing.event.OrderEvent;
import id.yuadit.learningeventsourcing.model.Order;
import id.yuadit.learningeventsourcing.repository.OrderEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderEventRepository eventRepository;

    public void createOrder(String customerId, String items) {
        Long nextOrderId = getNextOrderId();
        String eventData = String.format("{\"customerId\": \"%s\", \"items\": \"%s\"}", customerId, items);
        OrderEvent event = new OrderEvent(nextOrderId, "OrderCreated", eventData);
        eventRepository.save(event);
    }

    public void updateOrder(Long orderId, String customerId, String items) {
        String eventData = String.format("{\"customerId\": \"%s\", \"items\": \"%s\"}", customerId, items);
        OrderEvent event = new OrderEvent(orderId, "OrderUpdated", eventData);
        eventRepository.save(event);
    }

    public void deleteOrder(Long orderId) {
        OrderEvent event = new OrderEvent(orderId, "OrderDeleted", "{}");
        eventRepository.save(event);
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
