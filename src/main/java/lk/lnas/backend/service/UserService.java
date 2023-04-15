package lk.lnas.backend.service;

import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lk.lnas.backend.domain.Admin;
import lk.lnas.backend.domain.Customer;
import lk.lnas.backend.domain.Employee;
import lk.lnas.backend.domain.Organization;
import lk.lnas.backend.domain.Role;
import lk.lnas.backend.domain.Supplier;
import lk.lnas.backend.domain.User;
import lk.lnas.backend.model.UserDTO;
import lk.lnas.backend.repos.AdminRepository;
import lk.lnas.backend.repos.CustomerRepository;
import lk.lnas.backend.repos.EmployeeRepository;
import lk.lnas.backend.repos.OrganizationRepository;
import lk.lnas.backend.repos.RoleRepository;
import lk.lnas.backend.repos.SupplierRepository;
import lk.lnas.backend.repos.UserRepository;
import lk.lnas.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final SupplierRepository supplierRepository;
    private final OrganizationRepository organizationRepository;

    public UserService(final UserRepository userRepository, final RoleRepository roleRepository,
            final EmployeeRepository employeeRepository, final AdminRepository adminRepository,
            final CustomerRepository customerRepository,
            final SupplierRepository supplierRepository,
            final OrganizationRepository organizationRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
        this.supplierRepository = supplierRepository;
        this.organizationRepository = organizationRepository;
    }

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map((user) -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }

    public void delete(final Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPhone());
        userDTO.setIsEmailVerified(user.getIsEmailVerified());
        userDTO.setIsPhoneVerified(user.getIsPhoneVerified());
        userDTO.setIsBanned(user.getIsBanned());
        userDTO.setIsApproved(user.getIsApproved());
        userDTO.setRoleId(user.getRoleId() == null ? null : user.getRoleId().stream()
                .map(role -> role.getId())
                .toList());
        userDTO.setEmpID(user.getEmpID() == null ? null : user.getEmpID().getId());
        userDTO.setAdminID(user.getAdminID() == null ? null : user.getAdminID().getId());
        userDTO.setCustomerID(user.getCustomerID() == null ? null : user.getCustomerID().getId());
        userDTO.setSupplierId(user.getSupplierId() == null ? null : user.getSupplierId().getId());
        userDTO.setOrgID(user.getOrgID() == null ? null : user.getOrgID().getId());
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setIsEmailVerified(userDTO.getIsEmailVerified());
        user.setIsPhoneVerified(userDTO.getIsPhoneVerified());
        user.setIsBanned(userDTO.getIsBanned());
        user.setIsApproved(userDTO.getIsApproved());
        final List<Role> roleId = roleRepository.findAllById(
                userDTO.getRoleId() == null ? Collections.emptyList() : userDTO.getRoleId());
        if (roleId.size() != (userDTO.getRoleId() == null ? 0 : userDTO.getRoleId().size())) {
            throw new NotFoundException("one of roleId not found");
        }
        user.setRoleId(roleId.stream().collect(Collectors.toSet()));
        final Employee empID = userDTO.getEmpID() == null ? null : employeeRepository.findById(userDTO.getEmpID())
                .orElseThrow(() -> new NotFoundException("empID not found"));
        user.setEmpID(empID);
        final Admin adminID = userDTO.getAdminID() == null ? null : adminRepository.findById(userDTO.getAdminID())
                .orElseThrow(() -> new NotFoundException("adminID not found"));
        user.setAdminID(adminID);
        final Customer customerID = userDTO.getCustomerID() == null ? null : customerRepository.findById(userDTO.getCustomerID())
                .orElseThrow(() -> new NotFoundException("customerID not found"));
        user.setCustomerID(customerID);
        final Supplier supplierId = userDTO.getSupplierId() == null ? null : supplierRepository.findById(userDTO.getSupplierId())
                .orElseThrow(() -> new NotFoundException("supplierId not found"));
        user.setSupplierId(supplierId);
        final Organization orgID = userDTO.getOrgID() == null ? null : organizationRepository.findById(userDTO.getOrgID())
                .orElseThrow(() -> new NotFoundException("orgID not found"));
        user.setOrgID(orgID);
        return user;
    }

}
