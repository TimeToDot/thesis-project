package thesis.data.account.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "sex")
public class Sex {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer serialId;

    private String id;

    private String name;
}
