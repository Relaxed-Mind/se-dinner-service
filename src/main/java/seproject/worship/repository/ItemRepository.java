package seproject.worship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.worship.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
