package lk.lnas.backend.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PermissionDTO {

    private Long id;

    @Size(max = 255)
    private String code;

    @Size(max = 255)
    private String description;

}
