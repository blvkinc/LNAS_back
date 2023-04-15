package lk.lnas.backend.service;

import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lk.lnas.backend.domain.Employee;
import lk.lnas.backend.domain.Farm;
import lk.lnas.backend.model.EmployeeDTO;
import lk.lnas.backend.repos.EmployeeRepository;
import lk.lnas.backend.repos.FarmRepository;
import lk.lnas.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Transactional
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final FarmRepository farmRepository;

    public EmployeeService(final EmployeeRepository employeeRepository,
            final FarmRepository farmRepository) {
        this.employeeRepository = employeeRepository;
        this.farmRepository = farmRepository;
    }

    public List<EmployeeDTO> findAll() {
        final List<Employee> employees = employeeRepository.findAll(Sort.by("id"));
        return employees.stream()
                .map((employee) -> mapToDTO(employee, new EmployeeDTO()))
                .toList();
    }

    public EmployeeDTO get(final Long id) {
        return employeeRepository.findById(id)
                .map(employee -> mapToDTO(employee, new EmployeeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EmployeeDTO employeeDTO) {
        final Employee employee = new Employee();
        mapToEntity(employeeDTO, employee);
        return employeeRepository.save(employee).getId();
    }

    public void update(final Long id, final EmployeeDTO employeeDTO) {
        final Employee employee = employeeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(employeeDTO, employee);
        employeeRepository.save(employee);
    }

    public void delete(final Long id) {
        employeeRepository.deleteById(id);
    }

    private EmployeeDTO mapToDTO(final Employee employee, final EmployeeDTO employeeDTO) {
        employeeDTO.setId(employee.getId());
        employeeDTO.setStatus(employee.getStatus());
        employeeDTO.setFarmID(employee.getFarmID() == null ? null : employee.getFarmID().stream()
                .map(farm -> farm.getId())
                .toList());
        return employeeDTO;
    }

    private Employee mapToEntity(final EmployeeDTO employeeDTO, final Employee employee) {
        employee.setStatus(employeeDTO.getStatus());
        final List<Farm> farmID = farmRepository.findAllById(
                employeeDTO.getFarmID() == null ? Collections.emptyList() : employeeDTO.getFarmID());
        if (farmID.size() != (employeeDTO.getFarmID() == null ? 0 : employeeDTO.getFarmID().size())) {
            throw new NotFoundException("one of farmID not found");
        }
        employee.setFarmID(farmID.stream().collect(Collectors.toSet()));
        return employee;
    }

}
