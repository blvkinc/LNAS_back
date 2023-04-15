package lk.lnas.backend.repos;

import lk.lnas.backend.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
