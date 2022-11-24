package seproject.worship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.worship.domain.entity.ModifiedItem;

import java.util.List;

public interface ModifiedItemRepository extends JpaRepository<ModifiedItem, Long> {
    List<ModifiedItem> findAllByCartMenuId(Long cartMenuId);
    List<ModifiedItem> findAllByOrderMenuId(Long orderMenuId);
}
