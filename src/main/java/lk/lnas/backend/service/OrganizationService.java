package lk.lnas.backend.service;

import java.util.List;
import lk.lnas.backend.domain.Organization;
import lk.lnas.backend.model.OrganizationDTO;
import lk.lnas.backend.repos.OrganizationRepository;
import lk.lnas.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService(final OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public List<OrganizationDTO> findAll() {
        final List<Organization> organizations = organizationRepository.findAll(Sort.by("id"));
        return organizations.stream()
                .map((organization) -> mapToDTO(organization, new OrganizationDTO()))
                .toList();
    }

    public OrganizationDTO get(final Long id) {
        return organizationRepository.findById(id)
                .map(organization -> mapToDTO(organization, new OrganizationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OrganizationDTO organizationDTO) {
        final Organization organization = new Organization();
        mapToEntity(organizationDTO, organization);
        return organizationRepository.save(organization).getId();
    }

    public void update(final Long id, final OrganizationDTO organizationDTO) {
        final Organization organization = organizationRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(organizationDTO, organization);
        organizationRepository.save(organization);
    }

    public void delete(final Long id) {
        organizationRepository.deleteById(id);
    }

    private OrganizationDTO mapToDTO(final Organization organization,
            final OrganizationDTO organizationDTO) {
        organizationDTO.setId(organization.getId());
        organizationDTO.setName(organization.getName());
        organizationDTO.setDescription(organization.getDescription());
        organizationDTO.setProfile(organization.getProfile());
        return organizationDTO;
    }

    private Organization mapToEntity(final OrganizationDTO organizationDTO,
            final Organization organization) {
        organization.setName(organizationDTO.getName());
        organization.setDescription(organizationDTO.getDescription());
        organization.setProfile(organizationDTO.getProfile());
        return organization;
    }

}
