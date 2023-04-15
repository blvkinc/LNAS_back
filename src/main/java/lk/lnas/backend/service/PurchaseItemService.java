package lk.lnas.backend.service;

import java.util.List;
import lk.lnas.backend.domain.Purchase;
import lk.lnas.backend.domain.PurchaseItem;
import lk.lnas.backend.model.PurchaseItemDTO;
import lk.lnas.backend.repos.PurchaseItemRepository;
import lk.lnas.backend.repos.PurchaseRepository;
import lk.lnas.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PurchaseItemService {

    private final PurchaseItemRepository purchaseItemRepository;
    private final PurchaseRepository purchaseRepository;

    public PurchaseItemService(final PurchaseItemRepository purchaseItemRepository,
            final PurchaseRepository purchaseRepository) {
        this.purchaseItemRepository = purchaseItemRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public List<PurchaseItemDTO> findAll() {
        final List<PurchaseItem> purchaseItems = purchaseItemRepository.findAll(Sort.by("id"));
        return purchaseItems.stream()
                .map((purchaseItem) -> mapToDTO(purchaseItem, new PurchaseItemDTO()))
                .toList();
    }

    public PurchaseItemDTO get(final Long id) {
        return purchaseItemRepository.findById(id)
                .map(purchaseItem -> mapToDTO(purchaseItem, new PurchaseItemDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PurchaseItemDTO purchaseItemDTO) {
        final PurchaseItem purchaseItem = new PurchaseItem();
        mapToEntity(purchaseItemDTO, purchaseItem);
        return purchaseItemRepository.save(purchaseItem).getId();
    }

    public void update(final Long id, final PurchaseItemDTO purchaseItemDTO) {
        final PurchaseItem purchaseItem = purchaseItemRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(purchaseItemDTO, purchaseItem);
        purchaseItemRepository.save(purchaseItem);
    }

    public void delete(final Long id) {
        purchaseItemRepository.deleteById(id);
    }

    private PurchaseItemDTO mapToDTO(final PurchaseItem purchaseItem,
            final PurchaseItemDTO purchaseItemDTO) {
        purchaseItemDTO.setId(purchaseItem.getId());
        purchaseItemDTO.setPrice(purchaseItem.getPrice());
        purchaseItemDTO.setDiscount(purchaseItem.getDiscount());
        purchaseItemDTO.setQty(purchaseItem.getQty());
        purchaseItemDTO.setDescription(purchaseItem.getDescription());
        purchaseItemDTO.setPurchaseID(purchaseItem.getPurchaseID() == null ? null : purchaseItem.getPurchaseID().getId());
        return purchaseItemDTO;
    }

    private PurchaseItem mapToEntity(final PurchaseItemDTO purchaseItemDTO,
            final PurchaseItem purchaseItem) {
        purchaseItem.setPrice(purchaseItemDTO.getPrice());
        purchaseItem.setDiscount(purchaseItemDTO.getDiscount());
        purchaseItem.setQty(purchaseItemDTO.getQty());
        purchaseItem.setDescription(purchaseItemDTO.getDescription());
        final Purchase purchaseID = purchaseItemDTO.getPurchaseID() == null ? null : purchaseRepository.findById(purchaseItemDTO.getPurchaseID())
                .orElseThrow(() -> new NotFoundException("purchaseID not found"));
        purchaseItem.setPurchaseID(purchaseID);
        return purchaseItem;
    }

}
