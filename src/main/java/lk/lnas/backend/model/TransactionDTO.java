package lk.lnas.backend.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransactionDTO {

    private Long id;
    private Long amount;
    private LocalDateTime transactionDT;
    private TransactionMethod method;

}
