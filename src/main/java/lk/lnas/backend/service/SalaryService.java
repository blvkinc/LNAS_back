package lk.lnas.backend.service;

import java.util.List;
import lk.lnas.backend.domain.Admin;
import lk.lnas.backend.domain.Employee;
import lk.lnas.backend.domain.Salary;
import lk.lnas.backend.model.SalaryDTO;
import lk.lnas.backend.repos.AdminRepository;
import lk.lnas.backend.repos.EmployeeRepository;
import lk.lnas.backend.repos.SalaryRepository;
import lk.lnas.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SalaryService {

    private final SalaryRepository salaryRepository;
    private final EmployeeRepository employeeRepository;
    private final AdminRepository adminRepository;

    public SalaryService(final SalaryRepository salaryRepository,
            final EmployeeRepository employeeRepository, final AdminRepository adminRepository) {
        this.salaryRepository = salaryRepository;
        this.employeeRepository = employeeRepository;
        this.adminRepository = adminRepository;
    }

    public List<SalaryDTO> findAll() {
        final List<Salary> salarys = salaryRepository.findAll(Sort.by("id"));
        return salarys.stream()
                .map((salary) -> mapToDTO(salary, new SalaryDTO()))
                .toList();
    }

    public SalaryDTO get(final Long id) {
        return salaryRepository.findById(id)
                .map(salary -> mapToDTO(salary, new SalaryDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SalaryDTO salaryDTO) {
        final Salary salary = new Salary();
        mapToEntity(salaryDTO, salary);
        return salaryRepository.save(salary).getId();
    }

    public void update(final Long id, final SalaryDTO salaryDTO) {
        final Salary salary = salaryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(salaryDTO, salary);
        salaryRepository.save(salary);
    }

    public void delete(final Long id) {
        salaryRepository.deleteById(id);
    }

    private SalaryDTO mapToDTO(final Salary salary, final SalaryDTO salaryDTO) {
        salaryDTO.setId(salary.getId());
        salaryDTO.setAmount(salary.getAmount());
        salaryDTO.setPaidOn(salary.getPaidOn());
        salaryDTO.setDescription(salary.getDescription());
        salaryDTO.setStatus(salary.getStatus());
        salaryDTO.setEmpID(salary.getEmpID() == null ? null : salary.getEmpID().getId());
        salaryDTO.setAdminID(salary.getAdminID() == null ? null : salary.getAdminID().getId());
        return salaryDTO;
    }

    private Salary mapToEntity(final SalaryDTO salaryDTO, final Salary salary) {
        salary.setAmount(salaryDTO.getAmount());
        salary.setPaidOn(salaryDTO.getPaidOn());
        salary.setDescription(salaryDTO.getDescription());
        salary.setStatus(salaryDTO.getStatus());
        final Employee empID = salaryDTO.getEmpID() == null ? null : employeeRepository.findById(salaryDTO.getEmpID())
                .orElseThrow(() -> new NotFoundException("empID not found"));
        salary.setEmpID(empID);
        final Admin adminID = salaryDTO.getAdminID() == null ? null : adminRepository.findById(salaryDTO.getAdminID())
                .orElseThrow(() -> new NotFoundException("adminID not found"));
        salary.setAdminID(adminID);
        return salary;
    }

}
