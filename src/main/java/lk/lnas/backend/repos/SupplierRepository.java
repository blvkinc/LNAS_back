package lk.lnas.backend.repos;

import lk.lnas.backend.domain.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
