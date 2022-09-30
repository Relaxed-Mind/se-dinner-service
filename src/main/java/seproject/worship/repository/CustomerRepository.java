package seproject.worship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.worship.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
