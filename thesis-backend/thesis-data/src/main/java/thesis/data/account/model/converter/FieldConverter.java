package thesis.data.account.model.converter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import thesis.data.security.AsymmetricEncryptor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Slf4j
@AllArgsConstructor
@Converter
public class FieldConverter implements AttributeConverter<String , String> {

    private final AsymmetricEncryptor asymmetricEncryptor;
    @Override
    public String convertToDatabaseColumn(String text) {
        log.info("text to encrypt: " + text);

        if (text == null) {
            return null;
        }
        if (text.isEmpty()){
            return text;
        }

        var encryptedText = asymmetricEncryptor.encryptText(text);

        log.info("encrypted text: " + text);

        return encryptedText;
    }

    @Override
    public String convertToEntityAttribute(String text) {
        log.info("text to decrypt: " + text);

        if (text == null) {
            return null;
        }
        if (text.isEmpty()){
            return text;
        }

        var decryptedText = asymmetricEncryptor.decryptText(text);

        log.info("decrypted text: " + text);

        return decryptedText;
    }
}
