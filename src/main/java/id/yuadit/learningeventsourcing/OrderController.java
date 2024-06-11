package id.yuadit.learningeventsourcing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public CustomerOrder createOrder(@RequestParam Long customerId, @RequestParam String items) {
        return orderService.createOrder(customerId, items);
    }

    @GetMapping
    public List<CustomerOrder> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Optional<CustomerOrder> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/{id}")
    public CustomerOrder updateOrder(@PathVariable Long id, @RequestParam Long customerId, @RequestParam String items) {
        return orderService.updateOrder(id, customerId, items);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
