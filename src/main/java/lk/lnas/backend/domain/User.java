package lk.lnas.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class User {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private Boolean isEmailVerified;

    @Column
    private Boolean isPhoneVerified;

    @Column
    private Boolean isBanned;

    @Column
    private Boolean isApproved;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roleId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empid_id", unique = true)
    private Employee empID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adminid_id", unique = true)
    private Admin adminID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerid_id", unique = true)
    private Customer customerID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id_id", unique = true)
    private Supplier supplierId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orgid_id")
    private Organization orgID;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
