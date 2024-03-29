package pl.thesis.data.account.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import pl.thesis.data.converter.FieldConverter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@SuperBuilder
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "account_details")
public class AccountDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    @NotBlank(message = "name is required")
    private String name;

    private String middleName;

    @NotBlank(message = "surname is required")
    private String surname;

    @Convert(converter = FieldConverter.class)
    @NotBlank(message = "pesel is required")
    private String pesel;

    @ManyToOne
    @JoinColumn(name = "sex_id")
    private Sex sex;

    @NotBlank(message = "phonenumber is required")
    private String phoneNumber;

    @Convert(converter = FieldConverter.class)
    @NotBlank(message = "taxNumber is required")
    private String taxNumber;

    @Convert(converter = FieldConverter.class)
    private String idCardNumber;

    @NotBlank(message = "street is required")
    private String street;

    private String houseNumber;

    private String apartmentNumber;

    @NotBlank(message = "street is required")
    private String postalCode;

    @NotBlank(message = "city is required")
    private String city;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    private String privateEmail;

    @Temporal(TemporalType.TIMESTAMP)
    private Date employmentDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date exitDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

    @Convert(converter = FieldConverter.class)
    private String birthPlace;

    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "contract_type_id")
    private ContractType contractType;

    private Integer workingTime;

    private Integer wage;

    private Integer payday;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        AccountDetails that = (AccountDetails) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}