//package ch.finecloud.babytracker.data.repository;
//
//import ch.finecloud.babytracker.TestBabyTrackerApplication;
//import ch.finecloud.babytracker.bootstrap.BootstrapData;
//import ch.finecloud.babytracker.data.entity.Baby;
//import ch.finecloud.babytracker.data.entity.UserAccount;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.context.annotation.Import;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Import({TestBabyTrackerApplication.class, BootstrapData.class})
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class BabyRepositoryTest {
//
//    @Autowired
//    private BabyRepository babyRepository;
//
//    @Test
//    void searchBabiesByUserAccount_Email() {
//        UserAccount userAccount = new UserAccount("
//    }
//
//    @Test
//    void findBabyByUserAccount_Email() {
//    }
//}