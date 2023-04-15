package lk.lnas.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lk.lnas.backend.model.FarmDTO;
import lk.lnas.backend.service.FarmService;
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
@RequestMapping(value = "/api/farms", produces = MediaType.APPLICATION_JSON_VALUE)
public class FarmResource {

    private final FarmService farmService;

    public FarmResource(final FarmService farmService) {
        this.farmService = farmService;
    }

    @GetMapping
    public ResponseEntity<List<FarmDTO>> getAllFarms() {
        return ResponseEntity.ok(farmService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FarmDTO> getFarm(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(farmService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createFarm(@RequestBody @Valid final FarmDTO farmDTO) {
        return new ResponseEntity<>(farmService.create(farmDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFarm(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final FarmDTO farmDTO) {
        farmService.update(id, farmDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFarm(@PathVariable(name = "id") final Long id) {
        farmService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
