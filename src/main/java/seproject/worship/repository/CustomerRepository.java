package seproject.worship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import seproject.worship.domain.entity.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCidAndPw(String cid, String pw);
    Optional<Customer> findByCid(String cid);

    Optional<Customer> findByPhoneNum(String phoneNum);
}
