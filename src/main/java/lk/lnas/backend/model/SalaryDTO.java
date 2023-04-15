package lk.lnas.backend.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SalaryDTO {

    private Long id;

    @NotNull
    private Long amount;

    @NotNull
    private LocalDateTime paidOn;

    @NotNull
    @Size(max = 255)
    private String description;

    @NotNull
    private SalaryStatus status;

    private Long empID;

    private Long adminID;

}
