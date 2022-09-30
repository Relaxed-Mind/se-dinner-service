package seproject.worship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.worship.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
