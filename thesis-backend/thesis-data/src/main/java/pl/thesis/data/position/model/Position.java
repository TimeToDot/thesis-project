package pl.thesis.data.position.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.thesis.data.account.model.Account;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@ToString
@RequiredArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToMany(mappedBy="position",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PositionType status;

    private Date creationDate;

    private Date archiveDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Position position = (Position) o;
        return id != null && Objects.equals(id, position.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accounts, name, description, status);
    }
}
