package lk.lnas.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import lk.lnas.backend.model.SalaryStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Salary {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private LocalDateTime paidOn;

    @Column(nullable = false, name = "\"description\"")
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SalaryStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empid_id", unique = true)
    private Employee empID;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adminid_id", unique = true)
    private Admin adminID;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
