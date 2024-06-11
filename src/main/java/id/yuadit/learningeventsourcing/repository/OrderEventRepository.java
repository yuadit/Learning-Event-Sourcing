package id.yuadit.learningeventsourcing.repository;

import id.yuadit.learningeventsourcing.event.OrderEvent;
import id.yuadit.learningeventsourcing.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderEventRepository extends JpaRepository<OrderEvent, Long> {
    List<OrderEvent> findByOrderId(Long orderId);

    @Query("SELECT MAX(e.orderId) FROM OrderEvent e")
    Long findMaxOrderId();

    @Query("SELECT e FROM OrderEvent e WHERE e.orderId = :orderId AND e.id = (SELECT MAX(e2.id) FROM OrderEvent e2 WHERE e2.orderId = :orderId)")
    OrderEvent findLastEventByOrderId(@Param("orderId") Long orderId);

}
