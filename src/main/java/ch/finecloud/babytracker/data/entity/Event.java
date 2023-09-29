package ch.finecloud.babytracker.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class Event extends AbstractEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "baby_id")
    @NotNull
    @JsonIgnoreProperties({"events"})
    private Baby baby;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;


    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Baby getBaby() {
        return baby;
    }

    public void setBaby(Baby baby) {
        this.baby = baby;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
