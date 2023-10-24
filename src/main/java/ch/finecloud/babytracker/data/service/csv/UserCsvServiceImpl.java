//package ch.finecloud.babytracker.data.service.csv;
//
//import ch.finecloud.babytracker.data.model.UserAccountCSVRecord;
//import com.opencsv.bean.CsvToBeanBuilder;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.util.List;
//
//@Service
//public class UserCsvServiceImpl implements UserCsvService {
//    @Override
//    public List<UserAccountCSVRecord> convertCSV(File csvFile) {
//        try {
//            return new CsvToBeanBuilder<UserAccountCSVRecord>(new FileReader(csvFile))
//                    .withType(UserAccountCSVRecord.class)
//                    .build().parse();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException();
//        }
//    }
//}
