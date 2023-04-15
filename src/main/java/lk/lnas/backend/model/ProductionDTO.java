package lk.lnas.backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductionDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String description;

    @NotNull
    private Long productId;

    @NotNull
    private Long qty;

    private ProdStatus status;

    private Long plantId;

    private Long farmID;

}
