package ch.finecloud.babytracker.service.csv;


import ch.finecloud.babytracker.data.model.UserAccountCSVRecord;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Slf4j
@Service
public class UserCsvService {

    public List<UserAccountCSVRecord> convertCSV(File csvFile) {
        try {
            return new CsvToBeanBuilder<UserAccountCSVRecord>(new FileReader(csvFile))
                    .withType(UserAccountCSVRecord.class)
                    .build().parse();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        }
    }
}
