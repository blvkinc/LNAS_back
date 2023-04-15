package lk.lnas.backend.service;

import java.util.List;
import lk.lnas.backend.domain.Purchase;
import lk.lnas.backend.domain.Transaction;
import lk.lnas.backend.model.PurchaseDTO;
import lk.lnas.backend.repos.PurchaseRepository;
import lk.lnas.backend.repos.TransactionRepository;
import lk.lnas.backend.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final TransactionRepository transactionRepository;

    public PurchaseService(final PurchaseRepository purchaseRepository,
            final TransactionRepository transactionRepository) {
        this.purchaseRepository = purchaseRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<PurchaseDTO> findAll() {
        final List<Purchase> purchases = purchaseRepository.findAll(Sort.by("id"));
        return purchases.stream()
                .map((purchase) -> mapToDTO(purchase, new PurchaseDTO()))
                .toList();
    }

    public PurchaseDTO get(final Long id) {
        return purchaseRepository.findById(id)
                .map(purchase -> mapToDTO(purchase, new PurchaseDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PurchaseDTO purchaseDTO) {
        final Purchase purchase = new Purchase();
        mapToEntity(purchaseDTO, purchase);
        return purchaseRepository.save(purchase).getId();
    }

    public void update(final Long id, final PurchaseDTO purchaseDTO) {
        final Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(purchaseDTO, purchase);
        purchaseRepository.save(purchase);
    }

    public void delete(final Long id) {
        purchaseRepository.deleteById(id);
    }

    private PurchaseDTO mapToDTO(final Purchase purchase, final PurchaseDTO purchaseDTO) {
        purchaseDTO.setId(purchase.getId());
        purchaseDTO.setStatus(purchase.getStatus());
        purchaseDTO.setType(purchase.getType());
        purchaseDTO.setSubTotal(purchase.getSubTotal());
        purchaseDTO.setItemDiscount(purchase.getItemDiscount());
        purchaseDTO.setTax(purchase.getTax());
        purchaseDTO.setShipping(purchase.getShipping());
        purchaseDTO.setTotal(purchase.getTotal());
        purchaseDTO.setTransactionID(purchase.getTransactionID() == null ? null : purchase.getTransactionID().getId());
        return purchaseDTO;
    }

    private Purchase mapToEntity(final PurchaseDTO purchaseDTO, final Purchase purchase) {
        purchase.setStatus(purchaseDTO.getStatus());
        purchase.setType(purchaseDTO.getType());
        purchase.setSubTotal(purchaseDTO.getSubTotal());
        purchase.setItemDiscount(purchaseDTO.getItemDiscount());
        purchase.setTax(purchaseDTO.getTax());
        purchase.setShipping(purchaseDTO.getShipping());
        purchase.setTotal(purchaseDTO.getTotal());
        final Transaction transactionID = purchaseDTO.getTransactionID() == null ? null : transactionRepository.findById(purchaseDTO.getTransactionID())
                .orElseThrow(() -> new NotFoundException("transactionID not found"));
        purchase.setTransactionID(transactionID);
        return purchase;
    }

}
