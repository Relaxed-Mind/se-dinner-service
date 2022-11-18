package seproject.worship.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import seproject.worship.entity.Order;
import seproject.worship.entity.OrderMenu;
import seproject.worship.enumpack.OrderStatus;

import java.util.Collection;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    List<Order> findByOrderStatus(OrderStatus orderStatus);
    List<Order> findAllByOrderStatusIn(List<OrderStatus> orderStatuses);

    List<Order> findAllByCustomerId(Long customerId);
}
