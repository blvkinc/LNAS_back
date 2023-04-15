package lk.lnas.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lk.lnas.backend.model.PurchaseDTO;
import lk.lnas.backend.service.PurchaseService;
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
@RequestMapping(value = "/api/purchases", produces = MediaType.APPLICATION_JSON_VALUE)
public class PurchaseResource {

    private final PurchaseService purchaseService;

    public PurchaseResource(final PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public ResponseEntity<List<PurchaseDTO>> getAllPurchases() {
        return ResponseEntity.ok(purchaseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseDTO> getPurchase(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(purchaseService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPurchase(@RequestBody @Valid final PurchaseDTO purchaseDTO) {
        return new ResponseEntity<>(purchaseService.create(purchaseDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePurchase(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PurchaseDTO purchaseDTO) {
        purchaseService.update(id, purchaseDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePurchase(@PathVariable(name = "id") final Long id) {
        purchaseService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
