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

        return asymmetricEncryptor.encryptText(text);
    }

    @Override
    public String convertToEntityAttribute(String text) {
        if (text == null) {
            return null;
        }
        if (text.isEmpty()){
            return text;
        }

        return asymmetricEncryptor.decryptText(text);
    }
}
