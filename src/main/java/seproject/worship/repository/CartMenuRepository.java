package seproject.worship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.worship.domain.entity.CartMenu;

import java.util.List;

public interface CartMenuRepository extends JpaRepository<CartMenu, Long> {

    List<CartMenu> findAllByCustomerId(Long id);
    List<CartMenu> findAllByCustomerIdAndMenuId(Long customerId, Long menuId);
}
