package id_ocr.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonPropertyOrder({"uid", "cnp", "firstName", "lastName"})
public class ResponseIdentityCard {
    private String uid;
    private String cardModel;
    private String cnp;
    private String lastName;
    private String firstName;
    private String series;
    private String number;
    private String address;
    private String sex;
    private String citizenship;
    private String birthPlace;
    private LocalDate validFrom;
    private LocalDate validTo;
    private String issuedBy;
    private LocalDate dateOfBirth;
    private String mrzLine1;
    private String mrzLine2;
    private String mrzLine3;
}
