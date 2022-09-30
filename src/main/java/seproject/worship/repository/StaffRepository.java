package seproject.worship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.worship.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {
}
