package lk.lnas.backend.service;

import java.util.List;
import lk.lnas.backend.domain.Farm;
import lk.lnas.backend.domain.Plant;
import lk.lnas.backend.domain.Production;
import lk.lnas.backend.model.ProductionDTO;
import lk.lnas.backend.repos.FarmRepository;
import lk.lnas.backend.repos.PlantRepository;
import lk.lnas.backend.repos.ProductionRepository;
import lk.lnas.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProductionService {

    private final ProductionRepository productionRepository;
    private final PlantRepository plantRepository;
    private final FarmRepository farmRepository;

    public ProductionService(final ProductionRepository productionRepository,
            final PlantRepository plantRepository, final FarmRepository farmRepository) {
        this.productionRepository = productionRepository;
        this.plantRepository = plantRepository;
        this.farmRepository = farmRepository;
    }

    public List<ProductionDTO> findAll() {
        final List<Production> productions = productionRepository.findAll(Sort.by("id"));
        return productions.stream()
                .map((production) -> mapToDTO(production, new ProductionDTO()))
                .toList();
    }

    public ProductionDTO get(final Long id) {
        return productionRepository.findById(id)
                .map(production -> mapToDTO(production, new ProductionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ProductionDTO productionDTO) {
        final Production production = new Production();
        mapToEntity(productionDTO, production);
        return productionRepository.save(production).getId();
    }

    public void update(final Long id, final ProductionDTO productionDTO) {
        final Production production = productionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(productionDTO, production);
        productionRepository.save(production);
    }

    public void delete(final Long id) {
        productionRepository.deleteById(id);
    }

    private ProductionDTO mapToDTO(final Production production, final ProductionDTO productionDTO) {
        productionDTO.setId(production.getId());
        productionDTO.setName(production.getName());
        productionDTO.setDescription(production.getDescription());
        productionDTO.setProductId(production.getProductId());
        productionDTO.setQty(production.getQty());
        productionDTO.setStatus(production.getStatus());
        productionDTO.setPlantId(production.getPlantId() == null ? null : production.getPlantId().getId());
        productionDTO.setFarmID(production.getFarmID() == null ? null : production.getFarmID().getId());
        return productionDTO;
    }

    private Production mapToEntity(final ProductionDTO productionDTO, final Production production) {
        production.setName(productionDTO.getName());
        production.setDescription(productionDTO.getDescription());
        production.setProductId(productionDTO.getProductId());
        production.setQty(productionDTO.getQty());
        production.setStatus(productionDTO.getStatus());
        final Plant plantId = productionDTO.getPlantId() == null ? null : plantRepository.findById(productionDTO.getPlantId())
                .orElseThrow(() -> new NotFoundException("plantId not found"));
        production.setPlantId(plantId);
        final Farm farmID = productionDTO.getFarmID() == null ? null : farmRepository.findById(productionDTO.getFarmID())
                .orElseThrow(() -> new NotFoundException("farmID not found"));
        production.setFarmID(farmID);
        return production;
    }

}
