package lk.lnas.backend.repos;

import lk.lnas.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
