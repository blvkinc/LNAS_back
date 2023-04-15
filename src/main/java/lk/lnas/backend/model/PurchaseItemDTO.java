package lk.lnas.backend.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PurchaseItemDTO {

    private Long id;

    private Long price;

    private Long discount;

    private Long qty;

    @Size(max = 255)
    private String description;

    private Long purchaseID;

}
