package lk.lnas.backend.repos;

import lk.lnas.backend.domain.Production;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductionRepository extends JpaRepository<Production, Long> {
}
