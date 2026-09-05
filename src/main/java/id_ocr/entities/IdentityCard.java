package id_ocr.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "identity_cards")
@Data
public class IdentityCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uid", nullable = false, unique = true, updatable = false, length = 36)
    private String uid;

    @Column(name = "card_model", nullable = false, length = 5)
    private String cardModel;

    @Column(name = "cnp", nullable = false, length = 13)
    @Size(min = 13, max = 13)
    private String cnp;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "series", length = 5)
    private String series;

    @Column(name = "number", nullable = false, length = 9)
    @Size(min = 6, max = 9)
    private String number;

    @Column(name = "address", length = 150)
    private String address;

    @Column(name = "sex", nullable = false, length = 1)
    private String sex;

    @Column(name = "citizenship", nullable = false, length = 25)
    private String citizenship;

    @Column(name = "birthplace", length = 50)
    private String birthPlace;

    @Column(name = "valid_from", nullable = false)
    @DateTimeFormat
    private LocalDate validFrom;

    @Column(name = "valid_to", nullable = false)
    @DateTimeFormat
    private LocalDate validTo;

    @Column(name = "issued_by", nullable = false, length = 25)
    private String issuedBy;

    @Column(name = "date_of_birth")
    @DateTimeFormat
    private LocalDate dateOfBirth;

    @Column(name = "mrz_line_1", nullable = false, length = 45)
    private String mrzLine1;

    @Column(name = "mrz_line_2", nullable = false, length = 45)
    private String mrzLine2;

    @Column(name = "mrz_line_3", length = 45)
    private String mrzLine3;

    @PrePersist
    public void prePersist() {
        if (uid == null || uid.isBlank()) {
            uid = UUID.randomUUID().toString();
        }
    }
}