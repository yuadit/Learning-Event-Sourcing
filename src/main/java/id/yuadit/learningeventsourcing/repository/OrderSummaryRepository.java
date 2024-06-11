package id.yuadit.learningeventsourcing.repository;

import id.yuadit.learningeventsourcing.model.OrderSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderSummaryRepository extends JpaRepository<OrderSummary, Long> {
}
