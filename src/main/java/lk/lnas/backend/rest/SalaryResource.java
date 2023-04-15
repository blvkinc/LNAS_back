package lk.lnas.backend.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lk.lnas.backend.model.SalaryDTO;
import lk.lnas.backend.service.SalaryService;
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
@RequestMapping(value = "/api/salarys", produces = MediaType.APPLICATION_JSON_VALUE)
public class SalaryResource {

    private final SalaryService salaryService;

    public SalaryResource(final SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping
    public ResponseEntity<List<SalaryDTO>> getAllSalarys() {
        return ResponseEntity.ok(salaryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaryDTO> getSalary(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(salaryService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createSalary(@RequestBody @Valid final SalaryDTO salaryDTO) {
        return new ResponseEntity<>(salaryService.create(salaryDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSalary(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final SalaryDTO salaryDTO) {
        salaryService.update(id, salaryDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSalary(@PathVariable(name = "id") final Long id) {
        salaryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
