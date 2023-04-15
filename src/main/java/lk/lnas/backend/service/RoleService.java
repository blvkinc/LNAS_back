package lk.lnas.backend.service;

import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lk.lnas.backend.domain.Permission;
import lk.lnas.backend.domain.Role;
import lk.lnas.backend.model.RoleDTO;
import lk.lnas.backend.repos.PermissionRepository;
import lk.lnas.backend.repos.RoleRepository;
import lk.lnas.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Transactional
@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleService(final RoleRepository roleRepository,
            final PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public List<RoleDTO> findAll() {
        final List<Role> roles = roleRepository.findAll(Sort.by("id"));
        return roles.stream()
                .map((role) -> mapToDTO(role, new RoleDTO()))
                .toList();
    }

    public RoleDTO get(final Long id) {
        return roleRepository.findById(id)
                .map(role -> mapToDTO(role, new RoleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RoleDTO roleDTO) {
        final Role role = new Role();
        mapToEntity(roleDTO, role);
        return roleRepository.save(role).getId();
    }

    public void update(final Long id, final RoleDTO roleDTO) {
        final Role role = roleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(roleDTO, role);
        roleRepository.save(role);
    }

    public void delete(final Long id) {
        roleRepository.deleteById(id);
    }

    private RoleDTO mapToDTO(final Role role, final RoleDTO roleDTO) {
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        roleDTO.setLevel(role.getLevel());
        roleDTO.setRolePermission(role.getRolePermission() == null ? null : role.getRolePermission().stream()
                .map(permission -> permission.getId())
                .toList());
        return roleDTO;
    }

    private Role mapToEntity(final RoleDTO roleDTO, final Role role) {
        role.setName(roleDTO.getName());
        role.setLevel(roleDTO.getLevel());
        final List<Permission> rolePermission = permissionRepository.findAllById(
                roleDTO.getRolePermission() == null ? Collections.emptyList() : roleDTO.getRolePermission());
        if (rolePermission.size() != (roleDTO.getRolePermission() == null ? 0 : roleDTO.getRolePermission().size())) {
            throw new NotFoundException("one of rolePermission not found");
        }
        role.setRolePermission(rolePermission.stream().collect(Collectors.toSet()));
        return role;
    }

}
