package ch.finecloud.babytracker.data.model;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BabyCSVRecord {
    @CsvBindByName
    private int row;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String birthday;
}
