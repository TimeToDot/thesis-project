package pl.thesis.data.converter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.thesis.data.security.AsymmetricEncryptor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Slf4j
@AllArgsConstructor
@Converter
public class FieldConverter implements AttributeConverter<String , String> {

    private final AsymmetricEncryptor asymmetricEncryptor;
    @Override
    public String convertToDatabaseColumn(String text) {
        if (text == null) {
            return null;
        }
        if (text.isEmpty()){
            return text;
        }
        log.info("text to encrypt: " + text);

        var encryptedText = asymmetricEncryptor.encryptText(text);

        log.info("encrypted text: " + encryptedText);

        return encryptedText;
    }

    @Override
    public String convertToEntityAttribute(String text) {
        if (text == null) {
            return null;
        }
        if (text.isEmpty()){
            return text;
        }
        log.info("text to decrypt: " + text);

        var decryptedText = asymmetricEncryptor.decryptText(text);

        log.info("decrypted text: " + decryptedText);

        return decryptedText;
    }
}
