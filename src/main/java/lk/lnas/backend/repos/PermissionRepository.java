package lk.lnas.backend.repos;

import lk.lnas.backend.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
