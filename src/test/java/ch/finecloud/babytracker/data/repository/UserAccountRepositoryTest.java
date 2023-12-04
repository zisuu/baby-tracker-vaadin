package ch.finecloud.babytracker.data.repository;

import ch.finecloud.babytracker.TestBabyTrackerApplication;
import ch.finecloud.babytracker.bootstrap.BootstrapData;
import ch.finecloud.babytracker.config.TestConfig;
import ch.finecloud.babytracker.data.entity.UserAccount;
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

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Import({TestBabyTrackerApplication.class, BootstrapData.class, TestConfig.class, BabyCsvService.class, UserCsvService.class, EventCsvService.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserAccountRepositoryTest {

    @Autowired
    private UserAccountRepository userAccountRepository;

    private UserAccount userAccount;

    @BeforeEach
    void setUp() {
        userAccount = getRandomUserAccount();
    }

    private UserAccount getRandomUserAccount() {
        long count = userAccountRepository.count();
        assertThat(count).isPositive();
        int randomIndex = new Random().nextInt((int) count);
        Slice<UserAccount> userAccountSlice = userAccountRepository.findAll(PageRequest.of(randomIndex, 1));
        return userAccountSlice.getContent().get(0);
    }

    @Test
    void findUserAccountByEmail() {
        assertThat(userAccount).isNotNull();
        Optional<UserAccount> userAccountByEmail = userAccountRepository.findUserAccountByEmail(userAccount.getEmail());
        assertThat(userAccountByEmail).isNotNull();
        assertThat(userAccountByEmail.get().getEmail()).isEqualTo(userAccount.getEmail());
    }

}