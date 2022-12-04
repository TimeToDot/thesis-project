package thesis.data.position.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@ToString
@RequiredArgsConstructor
@SuperBuilder
@Getter
@Entity
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String name;

    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Position position = (Position) o;
        return id != null && Objects.equals(id, position.id);
    }
}
