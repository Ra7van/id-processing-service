package id_ocr.mapper;

import id_ocr.dto.ResponseIdentityCard;
import id_ocr.entities.IdentityCard;
import org.springframework.stereotype.Component;

@Component
public class IdentityCardToResponseIdentityCard {
    public ResponseIdentityCard toEntity (IdentityCard id) {
        ResponseIdentityCard responseIdentityCard = new ResponseIdentityCard();

        responseIdentityCard.setUid(id.getUid());
        responseIdentityCard.setCardModel(id.getCardModel());
        responseIdentityCard.setCnp(id.getCnp());
        responseIdentityCard.setLastName(id.getLastName());
        responseIdentityCard.setFirstName(id.getFirstName());
        responseIdentityCard.setSeries(id.getSeries());
        responseIdentityCard.setNumber(id.getNumber());
        responseIdentityCard.setAddress(id.getAddress());
        responseIdentityCard.setSex(id.getSex());
        responseIdentityCard.setCitizenship(id.getCitizenship());
        responseIdentityCard.setBirthPlace(id.getBirthPlace());
        responseIdentityCard.setValidFrom(id.getValidFrom());
        responseIdentityCard.setValidTo(id.getValidTo());
        responseIdentityCard.setIssuedBy(id.getIssuedBy());
        responseIdentityCard.setDateOfBirth(id.getDateOfBirth());
        responseIdentityCard.setMrzLine1(id.getMrzLine1());
        responseIdentityCard.setMrzLine2(id.getMrzLine2());
        responseIdentityCard.setMrzLine3(id.getMrzLine3());

        return responseIdentityCard;
    }
}
