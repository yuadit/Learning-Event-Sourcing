package id.yuadit.learningeventsourcing.controller;

import id.yuadit.learningeventsourcing.event.OrderEvent;
import id.yuadit.learningeventsourcing.service.OrderService;
import id.yuadit.learningeventsourcing.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public void createOrder(@RequestParam String customerId, @RequestParam String items) {
        orderService.createOrder(customerId, items);
    }

    @PutMapping("/{orderId}")
    public void updateOrder(@PathVariable Long orderId, @RequestParam String customerId, @RequestParam String items) {
        orderService.updateOrder(orderId, customerId, items);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }

    @GetMapping("/{orderId}/history")
    public List<OrderEvent> getOrderHistory(@PathVariable Long orderId) {
        return orderService.getOrderHistory(orderId);
    }

    @GetMapping("/{orderId}/state")
    public Order getOrderState(@PathVariable Long orderId) {
        return orderService.rebuildOrderState(orderId);
    }

}
