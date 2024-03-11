package ch.finecloud.babytracker;

import ch.finecloud.babytracker.bootstrap.BootstrapData;
import ch.finecloud.babytracker.service.csv.BabyCsvService;
import ch.finecloud.babytracker.service.csv.EventCsvService;
import ch.finecloud.babytracker.service.csv.UserCsvService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({TestBabyTrackerApplication.class, BootstrapData.class, BabyCsvService.class, UserCsvService.class, EventCsvService.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BabyTrackerApplicationTests {

    @Test
    void contextLoads() {
    }

}
