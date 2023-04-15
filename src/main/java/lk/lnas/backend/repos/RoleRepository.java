package lk.lnas.backend.repos;

import lk.lnas.backend.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
}
