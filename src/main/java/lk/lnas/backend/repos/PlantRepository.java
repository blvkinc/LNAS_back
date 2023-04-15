package lk.lnas.backend.repos;

import lk.lnas.backend.domain.Plant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PlantRepository extends JpaRepository<Plant, Long> {
}
