package lk.lnas.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lk.lnas.backend.model.PlantDTO;
import lk.lnas.backend.service.PlantService;
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
@RequestMapping(value = "/api/plants", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlantResource {

    private final PlantService plantService;

    public PlantResource(final PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping
    public ResponseEntity<List<PlantDTO>> getAllPlants() {
        return ResponseEntity.ok(plantService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantDTO> getPlant(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(plantService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPlant(@RequestBody @Valid final PlantDTO plantDTO) {
        return new ResponseEntity<>(plantService.create(plantDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePlant(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PlantDTO plantDTO) {
        plantService.update(id, plantDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePlant(@PathVariable(name = "id") final Long id) {
        plantService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
