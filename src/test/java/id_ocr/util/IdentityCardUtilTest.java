package id_ocr.util;

import id_ocr.dto.Fields;
import id_ocr.dto.IdCardExtractionResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IdentityCardUtilTest {

    private IdentityCardUtil identityCardUtil;

    @BeforeEach
    void setUp() {
        identityCardUtil = new IdentityCardUtil();
    }

    @Test
    void shouldReturnTrueWhenCnpAndDatesAreValidTest() {
        IdCardExtractionResult result = mock(IdCardExtractionResult.class);
        Fields fields = mock(Fields.class);

        when(result.fields()).thenReturn(fields);
        when(fields.cnp()).thenReturn("5000101010021");
        when(fields.validFrom()).thenReturn("01.01.2020");
        when(fields.validTo()).thenReturn("01.01.2099");

        boolean actualResult = identityCardUtil.validate(result);

        assertTrue(actualResult);
    }

    @Test
    void shouldReturnFalseWhenCnpIsInvalidTest() {
        IdCardExtractionResult result = mock(IdCardExtractionResult.class);
        Fields fields = mock(Fields.class);

        when(result.fields()).thenReturn(fields);
        when(fields.cnp()).thenReturn("5000101010019");
        when(fields.validFrom()).thenReturn("01.01.2020");
        when(fields.validTo()).thenReturn("01.01.2099");

        boolean actualResult = identityCardUtil.validate(result);

        assertFalse(actualResult);
    }

    @Test
    void shouldReturnFalseWhenValidFromIsNotBeforeNowTest() {
        IdCardExtractionResult result = mock(IdCardExtractionResult.class);
        Fields fields = mock(Fields.class);

        when(result.fields()).thenReturn(fields);
        when(fields.cnp()).thenReturn("5000101010011");
        when(fields.validFrom()).thenReturn("01.01.2099");
        when(fields.validTo()).thenReturn("01.01.2100");

        boolean actualResult = identityCardUtil.validate(result);

        assertFalse(actualResult);
    }

    @Test
    void shouldReturnFalseWhenValidToIsNotAfterNowTest() {
        IdCardExtractionResult result = mock(IdCardExtractionResult.class);
        Fields fields = mock(Fields.class);

        when(result.fields()).thenReturn(fields);
        when(fields.cnp()).thenReturn("5000101010021");
        when(fields.validFrom()).thenReturn("01.01.2020");
        when(fields.validTo()).thenReturn("01.01.2021");

        boolean actualResult = identityCardUtil.validate(result);

        assertFalse(actualResult);
    }
}