package lk.lnas.backend.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PurchaseDTO {

    private Long id;

    private PurchaseStatus status;

    @Size(max = 255)
    private String type;

    private Long subTotal;

    private Long itemDiscount;

    private Long tax;

    private Long shipping;

    private Long total;

    private Long transactionID;

}
