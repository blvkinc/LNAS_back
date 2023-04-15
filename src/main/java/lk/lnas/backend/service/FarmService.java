package lk.lnas.backend.service;

import java.util.List;
import lk.lnas.backend.domain.Farm;
import lk.lnas.backend.model.FarmDTO;
import lk.lnas.backend.repos.FarmRepository;
import lk.lnas.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class FarmService {

    private final FarmRepository farmRepository;

    public FarmService(final FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    public List<FarmDTO> findAll() {
        final List<Farm> farms = farmRepository.findAll(Sort.by("id"));
        return farms.stream()
                .map((farm) -> mapToDTO(farm, new FarmDTO()))
                .toList();
    }

    public FarmDTO get(final Long id) {
        return farmRepository.findById(id)
                .map(farm -> mapToDTO(farm, new FarmDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final FarmDTO farmDTO) {
        final Farm farm = new Farm();
        mapToEntity(farmDTO, farm);
        return farmRepository.save(farm).getId();
    }

    public void update(final Long id, final FarmDTO farmDTO) {
        final Farm farm = farmRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(farmDTO, farm);
        farmRepository.save(farm);
    }

    public void delete(final Long id) {
        farmRepository.deleteById(id);
    }

    private FarmDTO mapToDTO(final Farm farm, final FarmDTO farmDTO) {
        farmDTO.setId(farm.getId());
        farmDTO.setName(farm.getName());
        farmDTO.setLocation(farm.getLocation());
        farmDTO.setDescription(farm.getDescription());
        farmDTO.setFieldStatus(farm.getFieldStatus());
        return farmDTO;
    }

    private Farm mapToEntity(final FarmDTO farmDTO, final Farm farm) {
        farm.setName(farmDTO.getName());
        farm.setLocation(farmDTO.getLocation());
        farm.setDescription(farmDTO.getDescription());
        farm.setFieldStatus(farmDTO.getFieldStatus());
        return farm;
    }

    public boolean nameExists(final String name) {
        return farmRepository.existsByNameIgnoreCase(name);
    }

}
