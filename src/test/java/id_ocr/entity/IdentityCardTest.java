package id_ocr.entity;

import id_ocr.entities.IdentityCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IdentityCardTest {

    @Test
    void shouldGenerateUidWhenUidIsNullTest() {
        IdentityCard identityCard = new IdentityCard();

        identityCard.setUid(null);
        identityCard.prePersist();

        assertNotNull(identityCard.getUid());
        assertFalse(identityCard.getUid().isBlank());
    }

    @Test
    void shouldGenerateUidWhenUidIsBlankTest() {
        IdentityCard identityCard = new IdentityCard();

        identityCard.setUid("   ");
        identityCard.prePersist();

        assertNotNull(identityCard.getUid());
        assertFalse(identityCard.getUid().isBlank());
    }

    @Test
    void shouldKeepUidWhenUidAlreadyExistsTest() {
        IdentityCard identityCard = new IdentityCard();
        identityCard.setUid("existing-uid");

        identityCard.prePersist();

        assertEquals("existing-uid", identityCard.getUid());
    }
}