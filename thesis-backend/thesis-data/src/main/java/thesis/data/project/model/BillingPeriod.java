package thesis.data.project.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(name = "billing_period")
public class BillingPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer serialId;

    private String id;

    private String name;
}
