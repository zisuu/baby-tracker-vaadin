package ch.finecloud.babytracker.data.model;

import ch.finecloud.babytracker.data.entity.EventType;
import ch.finecloud.babytracker.data.entity.Status;
import com.opencsv.bean.CsvBindByName;
import lombok.*;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventCSVRecord {
    @CsvBindByName
    private int row;
    @CsvBindByName
    private EventType eventType;
    @CsvBindByName
    private String startDate;
    @CsvBindByName
    private String endDate;
    @CsvBindByName
    private String notes;
    @CsvBindByName
    private Status status;
}
