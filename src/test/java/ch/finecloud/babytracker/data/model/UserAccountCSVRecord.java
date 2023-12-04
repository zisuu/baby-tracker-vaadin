package ch.finecloud.babytracker.data.model;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountCSVRecord {
    @CsvBindByName
    private int row;
    @CsvBindByName
    private String email;
    @CsvBindByName
    private String password;

}
