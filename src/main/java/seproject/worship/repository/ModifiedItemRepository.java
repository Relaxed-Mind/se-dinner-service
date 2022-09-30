package seproject.worship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.worship.entity.ModifiedItem;

public interface ModifiedItemRepository extends JpaRepository<ModifiedItem, Long> {
}
