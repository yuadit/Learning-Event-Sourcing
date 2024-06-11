package id.yuadit.learningeventsourcing;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder,Long> {
}
