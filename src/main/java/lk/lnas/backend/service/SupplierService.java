package lk.lnas.backend.service;

import java.util.List;
import lk.lnas.backend.domain.Supplier;
import lk.lnas.backend.model.SupplierDTO;
import lk.lnas.backend.repos.SupplierRepository;
import lk.lnas.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(final SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<SupplierDTO> findAll() {
        final List<Supplier> suppliers = supplierRepository.findAll(Sort.by("id"));
        return suppliers.stream()
                .map((supplier) -> mapToDTO(supplier, new SupplierDTO()))
                .toList();
    }

    public SupplierDTO get(final Long id) {
        return supplierRepository.findById(id)
                .map(supplier -> mapToDTO(supplier, new SupplierDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SupplierDTO supplierDTO) {
        final Supplier supplier = new Supplier();
        mapToEntity(supplierDTO, supplier);
        return supplierRepository.save(supplier).getId();
    }

    public void update(final Long id, final SupplierDTO supplierDTO) {
        final Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(supplierDTO, supplier);
        supplierRepository.save(supplier);
    }

    public void delete(final Long id) {
        supplierRepository.deleteById(id);
    }

    private SupplierDTO mapToDTO(final Supplier supplier, final SupplierDTO supplierDTO) {
        supplierDTO.setId(supplier.getId());
        supplierDTO.setStatus(supplier.getStatus());
        return supplierDTO;
    }

    private Supplier mapToEntity(final SupplierDTO supplierDTO, final Supplier supplier) {
        supplier.setStatus(supplierDTO.getStatus());
        return supplier;
    }

}
