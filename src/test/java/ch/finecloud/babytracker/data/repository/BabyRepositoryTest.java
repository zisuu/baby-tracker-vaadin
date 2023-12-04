package ch.finecloud.babytracker.data.repository;

import ch.finecloud.babytracker.TestBabyTrackerApplication;
import ch.finecloud.babytracker.bootstrap.BootstrapData;
import ch.finecloud.babytracker.config.TestConfig;
import ch.finecloud.babytracker.data.entity.Baby;
import ch.finecloud.babytracker.service.csv.BabyCsvService;
import ch.finecloud.babytracker.service.csv.EventCsvService;
import ch.finecloud.babytracker.service.csv.UserCsvService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Import({TestBabyTrackerApplication.class, BootstrapData.class, TestConfig.class, BabyCsvService.class, UserCsvService.class, EventCsvService.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BabyRepositoryTest {

    @Autowired
    private BabyRepository babyRepository;

    private Baby baby;

    @BeforeEach
    void setUp() {
        baby = getRandomBaby();
    }

    private Baby getRandomBaby() {
        long count = babyRepository.count();
        assertThat(count).isPositive();
        int randomIndex = new Random().nextInt((int) count);
        Slice<Baby> babySlice = babyRepository.findAll(PageRequest.of(randomIndex, 1));
        return babySlice.getContent().get(0);
    }

    @Test
    void searchBabiesByUserAccount_Email() {
        assertThat(baby).isNotNull();
        List<Baby> babies = babyRepository.searchBabiesByUserAccount_Email(baby.getName(), baby.getUserAccount().getEmail());
        assertThat(babies).isNotNull();
        assertThat(babies.stream().anyMatch(baby -> baby.getName().equals(this.baby.getName()))).isTrue();
    }

    @Test
    void findBabyByUserAccount_Email() {
        assertThat(baby).isNotNull();
        List<Baby> babies = babyRepository.findBabyByUserAccount_Email(this.baby.getUserAccount().getEmail());
        assertThat(babies).isNotNull();
        assertThat(babies.stream().anyMatch(baby -> baby.getName().equals(this.baby.getName()))).isTrue();
    }
}