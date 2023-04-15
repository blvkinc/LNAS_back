package lk.lnas.backend.repos;

import lk.lnas.backend.domain.Salary;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SalaryRepository extends JpaRepository<Salary, Long> {
}
