package thesis.data.account.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@SuperBuilder
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "contract_type")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer serialId;

    @NotBlank(message = "name is required")
    private String id;

    @NotBlank(message = "name is required")
    private String name;
}
