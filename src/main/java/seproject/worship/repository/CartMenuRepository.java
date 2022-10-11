package seproject.worship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.worship.entity.CartMenu;

public interface CartMenuRepository extends JpaRepository<CartMenu, Long> {
}
