package id.yuadit.learningeventsourcing.service;

import id.yuadit.learningeventsourcing.event.OrderEvent;
import id.yuadit.learningeventsourcing.model.OrderSummary;
import id.yuadit.learningeventsourcing.repository.OrderEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderProjectionService {

    @Autowired
    private OrderEventRepository orderEventRepository;

    private Map<Long, OrderSummary> orderSummaries = new HashMap<>();

    public OrderSummary getOrderSummary(Long orderId) {
        return orderSummaries.get(orderId);
    }

    public void updateProjection(Long orderId) {
        List<OrderEvent> events = orderEventRepository.findByOrderId(orderId);
        OrderSummary summary = new OrderSummary();
        for (OrderEvent event : events) {
            apply(event, summary);
        }
        orderSummaries.put(orderId, summary);
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
                orderSummaries.remove(event.getOrderId());
                break;
            case INVOICE_PAID:
                summary.setPaid(true);
                break;
        }
    }
}
