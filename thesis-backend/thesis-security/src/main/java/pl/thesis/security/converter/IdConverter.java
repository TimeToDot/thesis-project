package pl.thesis.security.converter;

import lombok.AllArgsConstructor;
import org.mapstruct.Named;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.thesis.security.services.model.ThesisId;
import pl.thesis.security.config.SymmetricEncryptor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class IdConverter implements Converter<String, ThesisId> {

    @Override
    public ThesisId convert(String s) {
        return new ThesisId(mapToId(s));
    }

    private final SymmetricEncryptor symmetricEncryptor;

    @Named("mapToId")
    public Long mapToId(String textId){
        return Long.parseLong(symmetricEncryptor.decrypt(textId));
    }
    @Named("mapToIdList")
    public List<Long> mapToIdList(List<String> list){
        return list.stream()
                .map(symmetricEncryptor::decrypt)
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    @Named("mapToText")
    public String mapToText(Long id){
        if (id == null){
            return null;
        }
        return symmetricEncryptor.encrypt(id);
    }

    @Named("mapProjectPrivileges")
    public Map<String, List<String>> mapProjectPrivileges(Map<Long, List<String>> projectPrivileges) {
        return projectPrivileges.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> symmetricEncryptor.encrypt(entry.getKey()),
                        Map.Entry::getValue
                ));
    }
}
