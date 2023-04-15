package lk.lnas.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lk.lnas.backend.model.ProductionDTO;
import lk.lnas.backend.service.ProductionService;
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
@RequestMapping(value = "/api/productions", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductionResource {

    private final ProductionService productionService;

    public ProductionResource(final ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping
    public ResponseEntity<List<ProductionDTO>> getAllProductions() {
        return ResponseEntity.ok(productionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductionDTO> getProduction(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(productionService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createProduction(
            @RequestBody @Valid final ProductionDTO productionDTO) {
        return new ResponseEntity<>(productionService.create(productionDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduction(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ProductionDTO productionDTO) {
        productionService.update(id, productionDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProduction(@PathVariable(name = "id") final Long id) {
        productionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
