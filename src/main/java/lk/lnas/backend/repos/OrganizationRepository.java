package lk.lnas.backend.repos;

import lk.lnas.backend.domain.Organization;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
