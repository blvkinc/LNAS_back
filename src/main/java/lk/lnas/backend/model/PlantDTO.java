package lk.lnas.backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PlantDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    private Long salesPrice;

    private Long purchasePrice;

    @NotNull
    private Long qtyAtHand;

    @NotNull
    private Long qtyPotential;

    @Size(max = 255)
    private String scientificName;

    @Size(max = 255)
    private String desciption;

    @NotNull
    private PlantStatus plantStatus;

}
