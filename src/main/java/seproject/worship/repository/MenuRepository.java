package seproject.worship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.worship.domain.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
