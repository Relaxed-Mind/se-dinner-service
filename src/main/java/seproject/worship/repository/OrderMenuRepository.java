package seproject.worship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.worship.entity.OrderMenu;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
}
