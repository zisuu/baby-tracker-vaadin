package ch.finecloud.babytracker.data.dto;

import ch.finecloud.babytracker.data.entity.EventType;

public record EventTypeNumber(EventType eventType, Long numberOfEvents) {
}
