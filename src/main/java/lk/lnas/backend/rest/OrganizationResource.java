package lk.lnas.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lk.lnas.backend.model.OrganizationDTO;
import lk.lnas.backend.service.OrganizationService;
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
@RequestMapping(value = "/api/organizations", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrganizationResource {

    private final OrganizationService organizationService;

    public OrganizationResource(final OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    public ResponseEntity<List<OrganizationDTO>> getAllOrganizations() {
        return ResponseEntity.ok(organizationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDTO> getOrganization(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(organizationService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createOrganization(
            @RequestBody @Valid final OrganizationDTO organizationDTO) {
        return new ResponseEntity<>(organizationService.create(organizationDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOrganization(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final OrganizationDTO organizationDTO) {
        organizationService.update(id, organizationDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteOrganization(@PathVariable(name = "id") final Long id) {
        organizationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
