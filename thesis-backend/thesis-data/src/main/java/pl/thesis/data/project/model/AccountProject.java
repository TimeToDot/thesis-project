package pl.thesis.data.project.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.thesis.data.account.model.Account;
import pl.thesis.data.role.model.Role;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(name = "account_project")
public class AccountProject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private Integer workingTime;

    private Integer modifier;

    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date exitDate;

    @Enumerated(EnumType.STRING)
    private ProjectAccountStatus status;

}
