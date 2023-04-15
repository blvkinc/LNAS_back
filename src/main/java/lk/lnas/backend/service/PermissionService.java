package lk.lnas.backend.service;

import java.util.List;
import lk.lnas.backend.domain.Permission;
import lk.lnas.backend.model.PermissionDTO;
import lk.lnas.backend.repos.PermissionRepository;
import lk.lnas.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(final PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public List<PermissionDTO> findAll() {
        final List<Permission> permissions = permissionRepository.findAll(Sort.by("id"));
        return permissions.stream()
                .map((permission) -> mapToDTO(permission, new PermissionDTO()))
                .toList();
    }

    public PermissionDTO get(final Long id) {
        return permissionRepository.findById(id)
                .map(permission -> mapToDTO(permission, new PermissionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PermissionDTO permissionDTO) {
        final Permission permission = new Permission();
        mapToEntity(permissionDTO, permission);
        return permissionRepository.save(permission).getId();
    }

    public void update(final Long id, final PermissionDTO permissionDTO) {
        final Permission permission = permissionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(permissionDTO, permission);
        permissionRepository.save(permission);
    }

    public void delete(final Long id) {
        permissionRepository.deleteById(id);
    }

    private PermissionDTO mapToDTO(final Permission permission, final PermissionDTO permissionDTO) {
        permissionDTO.setId(permission.getId());
        permissionDTO.setCode(permission.getCode());
        permissionDTO.setDescription(permission.getDescription());
        return permissionDTO;
    }

    private Permission mapToEntity(final PermissionDTO permissionDTO, final Permission permission) {
        permission.setCode(permissionDTO.getCode());
        permission.setDescription(permissionDTO.getDescription());
        return permission;
    }

}
