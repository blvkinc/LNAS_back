package lk.lnas.backend.repos;

import lk.lnas.backend.domain.Farm;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FarmRepository extends JpaRepository<Farm, Long> {

    boolean existsByNameIgnoreCase(String name);

}
