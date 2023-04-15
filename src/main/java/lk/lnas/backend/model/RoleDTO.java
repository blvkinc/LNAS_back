package lk.lnas.backend.model;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RoleDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    private Integer level;

    private List<Long> rolePermission;

}
