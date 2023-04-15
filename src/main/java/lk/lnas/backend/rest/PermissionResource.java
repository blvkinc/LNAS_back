package lk.lnas.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lk.lnas.backend.model.PermissionDTO;
import lk.lnas.backend.service.PermissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissionResource {

    private final PermissionService permissionService;

    public PermissionResource(final PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public ResponseEntity<List<PermissionDTO>> getAllPermissions() {
        return ResponseEntity.ok(permissionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionDTO> getPermission(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(permissionService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPermission(
            @RequestBody @Valid final PermissionDTO permissionDTO) {
        return new ResponseEntity<>(permissionService.create(permissionDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePermission(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PermissionDTO permissionDTO) {
        permissionService.update(id, permissionDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePermission(@PathVariable(name = "id") final Long id) {
        permissionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
