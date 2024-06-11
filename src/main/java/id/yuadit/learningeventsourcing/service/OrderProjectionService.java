package id.yuadit.learningeventsourcing.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import id.yuadit.learningeventsourcing.event.OrderEvent;
import id.yuadit.learningeventsourcing.model.OrderSummary;
import id.yuadit.learningeventsourcing.repository.OrderEventRepository;
import id.yuadit.learningeventsourcing.repository.OrderSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProjectionService {

    @Autowired
    private OrderSummaryRepository orderSummaryRepository;

    @Autowired
    private OrderEventRepository orderEventRepository;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @KafkaListener(topics = "order-events", groupId = "order-events-group")
    public void handleOrderEvent(String event) {
        try {
            // Convert JSON string to object
            System.out.println(event);
            OrderEvent orderEvent = objectMapper.readValue(event, OrderEvent.class);
            Long orderId = orderEvent.getOrderId();
            OrderSummary summary = orderSummaryRepository.findById(orderId).orElse(new OrderSummary());
            apply(orderEvent, summary);
            orderSummaryRepository.save(summary);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public OrderSummary getOrderSummary(Long orderId) {
        return orderSummaryRepository.findById(orderId).orElse(null);
    }

    public void updateProjection(Long orderId) {
        List<OrderEvent> events = orderEventRepository.findByOrderId(orderId);
        OrderSummary summary = new OrderSummary();
        for (OrderEvent event : events) {
            apply(event, summary);
        }
        orderSummaryRepository.save(summary);
    }

    private void apply(OrderEvent event, OrderSummary summary) {
        switch (event.getEventType()) {
            case ORDER_CREATED:
                summary.setOrderId(event.getOrderId());
                summary.setCustomerId(event.getCustomerId());
                summary.setItems(event.getItemsAsList());
                break;
            case ORDER_UPDATED:
                summary.setCustomerId(event.getCustomerId());
                summary.setItems(event.getItemsAsList());
                break;
            case ORDER_DELETED:
                orderSummaryRepository.deleteById(event.getOrderId());
                break;
            case INVOICE_PAID:
                summary.setPaid(event.isPaid());
                break;
        }
    }
}
