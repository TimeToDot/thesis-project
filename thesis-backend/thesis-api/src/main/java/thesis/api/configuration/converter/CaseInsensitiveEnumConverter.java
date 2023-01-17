package thesis.api.configuration.converter;

import org.springframework.core.convert.converter.Converter;

public class CaseInsensitiveEnumConverter<T extends Enum<T>> implements Converter<String, T> {
    private Class<T> enumClass;

    public CaseInsensitiveEnumConverter(Class<T> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public T convert(String source) {
        return T.valueOf(enumClass, source.toUpperCase());
    }
}
