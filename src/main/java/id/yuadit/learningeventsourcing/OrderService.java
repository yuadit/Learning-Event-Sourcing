package id.yuadit.learningeventsourcing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public CustomerOrder createOrder(Long customerId, String items) {
        CustomerOrder order = new CustomerOrder(customerId, items);
        return orderRepository.save(order);
    }

    public List<CustomerOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<CustomerOrder> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public CustomerOrder updateOrder(Long id, Long customerId, String items) {
        Optional<CustomerOrder> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            CustomerOrder order = optionalOrder.get();
            order.setCustomerId(customerId);
            order.setItems(items);
            return orderRepository.save(order);
        }
        return null;
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

}
