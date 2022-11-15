package seproject.worship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.worship.entity.OrderMenu;
import seproject.worship.enumpack.OrderStatus;
import seproject.worship.enumpack.StyleStatus;

import java.util.List;
import java.util.Optional;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
    List<OrderMenu> findAllByOrderId(Long orderId);
}
