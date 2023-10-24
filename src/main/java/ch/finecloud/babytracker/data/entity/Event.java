package ch.finecloud.babytracker.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Version
    private int version;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @Size(max = 255)
    @Column(length = 255)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "baby_id")
    @NotNull
    @JsonIgnoreProperties({"events"})
    private Baby baby;

    @Enumerated(EnumType.STRING)
    private Status status;
}
