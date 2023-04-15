package lk.lnas.backend.repos;

import lk.lnas.backend.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
