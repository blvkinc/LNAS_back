package lk.lnas.backend.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmployeeDTO {

    private Long id;
    private UserStatus status;
    private List<Long> farmID;

}
