package ch.finecloud.babytracker.bootstrap;

import ch.finecloud.babytracker.TestBabyTrackerApplication;
import ch.finecloud.babytracker.config.TestConfig;
import ch.finecloud.babytracker.data.repository.BabyRepository;
import ch.finecloud.babytracker.data.repository.EventRepository;
import ch.finecloud.babytracker.data.repository.UserAccountRepository;
import ch.finecloud.babytracker.service.csv.BabyCsvService;
import ch.finecloud.babytracker.service.csv.EventCsvService;
import ch.finecloud.babytracker.service.csv.UserCsvService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({TestBabyTrackerApplication.class, UserCsvService.class, BabyCsvService.class, EventCsvService.class, TestConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BootstrapDataTest {


    @Autowired
    BabyRepository babyRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    EventCsvService eventCsvService;

    @Autowired
    BabyCsvService babyCsvService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserCsvService userCsvService;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        bootstrapData = new BootstrapData(passwordEncoder, userAccountRepository, babyRepository, eventRepository, babyCsvService, userCsvService, eventCsvService);
    }

    @Test
    void Testrun() throws Exception {
        bootstrapData.run(null);
        assertThat(userAccountRepository.count()).isEqualTo(3);
        assertThat(babyRepository.count()).isEqualTo(2);
        assertThat(eventRepository.count()).isEqualTo(6);
    }
}