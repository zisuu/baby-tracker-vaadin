package ch.finecloud.babytracker.data.dto;

import java.sql.Date;
import java.time.LocalDate;

public record BabySleepPerDay(Date day, Long sleepDuration) {
}
