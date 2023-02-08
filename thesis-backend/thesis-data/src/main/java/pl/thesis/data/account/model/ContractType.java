package pl.thesis.data.account.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@ToString
@RequiredArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "contract_type")
public class ContractType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Integer id;

    @NotBlank(message = "name is required")
    private String name;
}
