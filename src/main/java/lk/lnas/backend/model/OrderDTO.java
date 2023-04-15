package lk.lnas.backend.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDTO {

    private Long id;

    private OrderStatus status;

    @Size(max = 255)
    private String type;

    private Long subtotal;

    private Long itemDiscount;

    private Long tax;

    private Long shipping;

    private Long total;

    private Long transacationID;

}
