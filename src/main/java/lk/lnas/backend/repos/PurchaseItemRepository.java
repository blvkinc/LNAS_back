package lk.lnas.backend.repos;

import lk.lnas.backend.domain.PurchaseItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, Long> {
}
