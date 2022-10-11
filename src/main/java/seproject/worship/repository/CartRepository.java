package seproject.worship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.worship.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
