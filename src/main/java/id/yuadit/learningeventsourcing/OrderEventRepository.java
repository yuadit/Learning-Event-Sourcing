package id.yuadit.learningeventsourcing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderEventRepository extends JpaRepository<OrderEvent, Long> {
    List<OrderEvent> findByOrderId(Long orderId);

    @Query("SELECT MAX(e.orderId) FROM OrderEvent e")
    Long findMaxOrderId();
}
