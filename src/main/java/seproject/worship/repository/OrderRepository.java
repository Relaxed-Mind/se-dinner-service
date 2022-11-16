package seproject.worship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.worship.entity.Order;
import seproject.worship.enumpack.OrderStatus;

import java.util.Collection;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOrderStatus(OrderStatus orderStatus);
    List<Order> findAllByOrderStatusIn(List<OrderStatus> orderStatuses);

    List<Order> findAllByCustomerId(Long customerId);
    Long countByCustomerIdAndOrderStatus(Long customerId, OrderStatus orderStatus);
}
