package pl.thesis.api.converter;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.thesis.security.config.SymmetricEncryptor;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class UuidConverter {

    @Autowired
    private SymmetricEncryptor symmetricEncryptor;

    @Named("mapToId")
    public UUID mapToId(String textId){
        return UUID.fromString(symmetricEncryptor.decrypt(textId));
    }
    @Named("mapToIdList")
    public List<UUID> mapToIdList(List<String> list){
        return list.stream()
                .map(s -> symmetricEncryptor.decrypt(s))
                .map(UUID::fromString)
                .collect(Collectors.toList());
    }

    @Named("mapToText")
    public String mapToText(UUID id){
        if (id == null){
            return null;
        }
        return symmetricEncryptor.encrypt(id);
    }

    @Named("mapProjectPrivileges")
    public Map<String, List<String>> mapProjectPrivileges(Map<UUID, List<String>> projectPrivileges){
        return projectPrivileges.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> symmetricEncryptor.encrypt(entry.getKey()),
                        Map.Entry::getValue
                ));
    }
}
