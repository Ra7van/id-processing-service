package id_ocr.mapper;

import id_ocr.dto.IdCardExtractionResult;
import id_ocr.entities.IdentityCard;
import org.springframework.stereotype.Component;
import id_ocr.util.DateUtil;

@Component
public class ExtractionToIdentityCard {
    private final DateUtil formatter;

    public ExtractionToIdentityCard(DateUtil formatter) {
        this.formatter = formatter;
    }

    public IdentityCard toEntity(IdCardExtractionResult result) {

        IdentityCard id = new IdentityCard();
        id.setCardModel(result.cardModel());
        id.setCnp(result.fields().cnp());
        id.setLastName(result.fields().lastName());
        id.setFirstName(result.fields().firstName());
        id.setSeries(result.fields().series());
        id.setNumber(result.fields().number());
        id.setAddress(result.fields().address());
        id.setSex(result.fields().sex());
        id.setCitizenship(result.fields().citizenship());
        id.setBirthPlace(result.fields().birthPlace());
        id.setValidFrom(formatter.parseData(result.fields().validFrom()));
        id.setValidTo(formatter.parseData(result.fields().validTo()));
        id.setIssuedBy(result.fields().issuedBy());
        id.setDateOfBirth(formatter.parseData(result.fields().dateOfBirth()));
        id.setMrzLine1(result.mrz().line1());
        id.setMrzLine2(result.mrz().line2());
        id.setMrzLine3(result.mrz().line3());

        return id;
    }
}
