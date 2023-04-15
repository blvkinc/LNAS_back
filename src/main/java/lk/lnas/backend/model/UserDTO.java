package lk.lnas.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Long id;

    @Size(max = 255)
    private String username;

    @Size(max = 255)
    private String password;

    @Size(max = 255)
    private String firstName;

    @Size(max = 255)
    private String lastName;

    @Size(max = 255)
    private String email;

    @Size(max = 255)
    private String phone;

    @JsonProperty("isEmailVerified")
    private Boolean isEmailVerified;

    @JsonProperty("isPhoneVerified")
    private Boolean isPhoneVerified;

    @JsonProperty("isBanned")
    private Boolean isBanned;

    @JsonProperty("isApproved")
    private Boolean isApproved;

    private List<Long> roleId;

    private Long empID;

    private Long adminID;

    private Long customerID;

    private Long supplierId;

    private Long orgID;

}
