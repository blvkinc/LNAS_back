package lk.lnas.backend.service;

import java.util.List;
import lk.lnas.backend.domain.Plant;
import lk.lnas.backend.model.PlantDTO;
import lk.lnas.backend.repos.PlantRepository;
import lk.lnas.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PlantService {

    private final PlantRepository plantRepository;

    public PlantService(final PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public List<PlantDTO> findAll() {
        final List<Plant> plants = plantRepository.findAll(Sort.by("id"));
        return plants.stream()
                .map((plant) -> mapToDTO(plant, new PlantDTO()))
                .toList();
    }

    public PlantDTO get(final Long id) {
        return plantRepository.findById(id)
                .map(plant -> mapToDTO(plant, new PlantDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PlantDTO plantDTO) {
        final Plant plant = new Plant();
        mapToEntity(plantDTO, plant);
        return plantRepository.save(plant).getId();
    }

    public void update(final Long id, final PlantDTO plantDTO) {
        final Plant plant = plantRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(plantDTO, plant);
        plantRepository.save(plant);
    }

    public void delete(final Long id) {
        plantRepository.deleteById(id);
    }

    private PlantDTO mapToDTO(final Plant plant, final PlantDTO plantDTO) {
        plantDTO.setId(plant.getId());
        plantDTO.setName(plant.getName());
        plantDTO.setSalesPrice(plant.getSalesPrice());
        plantDTO.setPurchasePrice(plant.getPurchasePrice());
        plantDTO.setQtyAtHand(plant.getQtyAtHand());
        plantDTO.setQtyPotential(plant.getQtyPotential());
        plantDTO.setScientificName(plant.getScientificName());
        plantDTO.setDesciption(plant.getDesciption());
        plantDTO.setPlantStatus(plant.getPlantStatus());
        return plantDTO;
    }

    private Plant mapToEntity(final PlantDTO plantDTO, final Plant plant) {
        plant.setName(plantDTO.getName());
        plant.setSalesPrice(plantDTO.getSalesPrice());
        plant.setPurchasePrice(plantDTO.getPurchasePrice());
        plant.setQtyAtHand(plantDTO.getQtyAtHand());
        plant.setQtyPotential(plantDTO.getQtyPotential());
        plant.setScientificName(plantDTO.getScientificName());
        plant.setDesciption(plantDTO.getDesciption());
        plant.setPlantStatus(plantDTO.getPlantStatus());
        return plant;
    }

}
