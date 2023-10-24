package ch.finecloud.babytracker.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Baby {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Version
    private int version;
    @NotBlank
    private String name;
    @NotNull
    private LocalDate birthday;

    @OneToMany(mappedBy = "baby")
    @Nullable
    private List<Event> events = new LinkedList<>();
    @ManyToOne
    @JoinColumn(name = "user_account_id")
    @NotNull
    @JsonIgnoreProperties({"babies"})
    private UserAccount userAccount;

//    @Formula("(select count(c.id) from Event c where c.baby_id = id)")
//    private int eventCount;
}
