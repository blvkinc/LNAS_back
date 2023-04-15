package lk.lnas.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lk.lnas.backend.model.PurchaseItemDTO;
import lk.lnas.backend.service.PurchaseItemService;
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
@RequestMapping(value = "/api/purchaseItems", produces = MediaType.APPLICATION_JSON_VALUE)
public class PurchaseItemResource {

    private final PurchaseItemService purchaseItemService;

    public PurchaseItemResource(final PurchaseItemService purchaseItemService) {
        this.purchaseItemService = purchaseItemService;
    }

    @GetMapping
    public ResponseEntity<List<PurchaseItemDTO>> getAllPurchaseItems() {
        return ResponseEntity.ok(purchaseItemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseItemDTO> getPurchaseItem(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(purchaseItemService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createPurchaseItem(
            @RequestBody @Valid final PurchaseItemDTO purchaseItemDTO) {
        return new ResponseEntity<>(purchaseItemService.create(purchaseItemDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePurchaseItem(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PurchaseItemDTO purchaseItemDTO) {
        purchaseItemService.update(id, purchaseItemDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePurchaseItem(@PathVariable(name = "id") final Long id) {
        purchaseItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
