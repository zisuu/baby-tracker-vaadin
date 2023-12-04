package ch.finecloud.babytracker.bootstrap;


import ch.finecloud.babytracker.data.entity.*;
import ch.finecloud.babytracker.data.model.BabyCSVRecord;
import ch.finecloud.babytracker.data.model.EventCSVRecord;
import ch.finecloud.babytracker.data.model.UserAccountCSVRecord;
import ch.finecloud.babytracker.data.repository.BabyRepository;
import ch.finecloud.babytracker.data.repository.EventRepository;
import ch.finecloud.babytracker.data.repository.UserAccountRepository;
import ch.finecloud.babytracker.service.csv.BabyCsvService;
import ch.finecloud.babytracker.service.csv.EventCsvService;
import ch.finecloud.babytracker.service.csv.UserCsvService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public static final String EMAIL = "userAccount1@example.com";
    private final UserAccountRepository userAccountRepository;
    private final BabyRepository babyRepository;
    private final EventRepository eventRepository;
    private final BabyCsvService babyCsvService;
    private final UserCsvService userCsvService;
    private final EventCsvService eventCsvService;


    @Transactional
    @Override
    public void run(String... args) throws Exception {
//        loadUserAccountData();
        loadCsvData();
//        loadBabyData();
//        loadEventData();
    }

//    private void loadUserAccountData() {
//        // TODO: add roles to those users and those in the CSV file
//        if (userAccountRepository.count() == 0) {
//            UserAccount userAccount1 = UserAccount.builder()
////                    .id(UUID.randomUUID())
////                    .version(1)
//                    .email("userAccount1@example.com")
//                    .password(passwordEncoder.encode("password1"))
////                    .createdDate(LocalDateTime.now())
////                    .lastModifiedDate(LocalDateTime.now())
//                    .role(Role.USER)
//                    .build();
//
//            UserAccount userAccount2 = UserAccount.builder()
//                    .id(UUID.randomUUID())
//                    .version(2)
//                    .email("userAccount2@example.com")
//                    .password(passwordEncoder.encode("password2"))
////                    .createdDate(LocalDateTime.now())
////                    .lastModifiedDate(LocalDateTime.now())
//                    .role(Role.USER)
//                    .build();
//
//            UserAccount userAccount3 = UserAccount.builder()
//                    .id(UUID.randomUUID())
//                    .version(3)
//                    .email("userAccount3@example.com")
//                    .password(passwordEncoder.encode("password3"))
////                    .createdDate(LocalDateTime.now())
////                    .lastModifiedDate(LocalDateTime.now())
//                    .role(Role.USER)
//                    .build();
//
//            userAccountRepository.save(userAccount1);
//            userAccountRepository.save(userAccount2);
//            userAccountRepository.save(userAccount3);
//        }
//    }


    private void loadCsvData() throws FileNotFoundException {
        if (userAccountRepository.count() < 10) {
            File file = ResourceUtils.getFile("classpath:csvdata/users.csv");

            List<UserAccountCSVRecord> userAccountCSVRecords = userCsvService.convertCSV(file);

            userAccountCSVRecords.forEach(userAccountCSVRecord -> {
                String email = userAccountCSVRecord.getEmail();
                String password = userAccountCSVRecord.getPassword();

                userAccountRepository.save(UserAccount.builder()
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .role(Role.USER)
                        .build());
            });
        }

        if (babyRepository.count() < 10) {
            File file = ResourceUtils.getFile("classpath:csvdata/babies.csv");

            List<BabyCSVRecord> babyCSVRecords = babyCsvService.convertCSV(file);

            babyCSVRecords.forEach(babyCSVRecord -> {
                String name = babyCSVRecord.getName();
                LocalDate birthday = LocalDate.parse(babyCSVRecord.getBirthday());

                babyRepository.save(Baby.builder()
                        .name(name)
                        .userAccount(userAccountRepository.findUserAccountByEmail("userAccount1@example.com").get())
                        .birthday(birthday)
                        .build());
            });
        }

        if (eventRepository.count() < 10) {
            File file = ResourceUtils.getFile("classpath:csvdata/events.csv");

            List<EventCSVRecord> eventCSVRecords = eventCsvService.convertCSV(file);

            eventCSVRecords.forEach(eventCSVRecord -> {
                LocalDateTime startDate = null;
                LocalDateTime endDate = null;
                EventType eventType = eventCSVRecord.getEventType();

                if (!Objects.equals(eventCSVRecord.getStartDate(), "") && eventCSVRecord.getStartDate() != null) {
                    startDate = LocalDateTime.parse(eventCSVRecord.getStartDate());
                }
                if (!Objects.equals(eventCSVRecord.getEndDate(), "") && eventCSVRecord.getEndDate() != null) {
                    endDate = LocalDateTime.parse(eventCSVRecord.getEndDate());
                }

                eventRepository.save(Event.builder()
                        .eventType(eventType)
                        .startDate(startDate)
                        .endDate(endDate)
                        .notes(eventCSVRecord.getNotes())
                        .status(eventCSVRecord.getStatus())
                        .baby(babyRepository.findBabyByName("Jobie").get())
                        .build());
            });

        }
    }

//    private void loadUserAccountData() {
//        if (userAccountRepository.count() == 0) {
//            UserAccount userAccount1 = UserAccount.builder()
//                    .id(UUID.randomUUID())
//                    .version(1)
//                    .email("userAccount1@example.com")
//                    .password(passwordEncoder.encode("password1"))
////                    .createdDate(LocalDateTime.now())
////                    .lastModifiedDate(LocalDateTime.now())
//                    .role(Role.USER)
//                    .build();
//
//            UserAccount userAccount2 = UserAccount.builder()
//                    .id(UUID.randomUUID())
//                    .version(2)
//                    .email("userAccount2@example.com")
//                    .password(passwordEncoder.encode("password2"))
////                    .createdDate(LocalDateTime.now())
////                    .lastModifiedDate(LocalDateTime.now())
//                    .role(Role.USER)
//                    .build();
//
//            UserAccount userAccount3 = UserAccount.builder()
//                    .id(UUID.randomUUID())
//                    .version(3)
//                    .email("userAccount3@example.com")
//                    .password(passwordEncoder.encode("password3"))
////                    .createdDate(LocalDateTime.now())
////                    .lastModifiedDate(LocalDateTime.now())
//                    .role(Role.USER)
//                    .build();
//
//            userAccountRepository.save(userAccount1);
//            userAccountRepository.save(userAccount2);
//            userAccountRepository.save(userAccount3);
//        }
//    }

//    private void loadBabyData() {
//        if (babyRepository.count() == 0) {
//            Baby baby1 = Baby.builder()
//                    .id(UUID.randomUUID())
//                    .version(1)
//                    .name("Max")
//                    .birthday(LocalDate.now().minusMonths(1))
//                    .userAccount(userAccountRepository.findUserAccountByEmail(EMAIL).get())
////                    .createdDate(LocalDateTime.now())
////                    .lastModifiedDate(LocalDateTime.now())
//                    .build();
//
//            Baby baby2 = Baby.builder()
//                    .id(UUID.randomUUID())
//                    .version(2)
//                    .name("Miriam")
//                    .birthday(LocalDate.now().minusMonths(2))
//                    .userAccount(userAccountRepository.findUserAccountByEmail(EMAIL).get())
////                    .createdDate(LocalDateTime.now())
////                    .lastModifiedDate(LocalDateTime.now())
//                    .build();
//
//            babyRepository.save(baby1);
//            babyRepository.save(baby2);
//        }
//    }
//
//
//    private void loadEventData() {
//        if (eventRepository.count() == 0) {
//            Event event1 = Event.builder()
//                    .id(UUID.randomUUID())
//                    .version(1)
//                    .eventType(EventType.SLEEPING)
//                    .startDate(LocalDateTime.now().minusHours(1))
//                    .endDate(LocalDateTime.now())
//                    .baby(babyRepository.findBabyByUserAccount_Email(EMAIL).get(0))
//                    .notes("Baby was sleeping")
//                    .status(Status.STARTED)
//                    .build();
//
//            Event event2 = Event.builder()
//                    .id(UUID.randomUUID())
//                    .version(1)
//                    .eventType(EventType.BEDTIME)
//                    .startDate(LocalDateTime.now().minusHours(4))
//                    .endDate(LocalDateTime.now())
//                    .baby(babyRepository.findBabyByUserAccount_Email(EMAIL).get(0))
//                    .notes("Baby was able to sleep quickly")
//                    .status(Status.COMPLETED)
//                    .build();
//
//            Event event3 = Event.builder()
//                    .id(UUID.randomUUID())
//                    .version(1)
//                    .eventType(EventType.SLEEPING)
//                    .startDate(LocalDateTime.now().minusHours(5))
//                    .endDate(LocalDateTime.now())
//                    .baby(babyRepository.findBabyByUserAccount_Email(EMAIL).get(0))
//                    .notes("Baby was already sleeping")
//                    .status(Status.INTERRUPTED)
//                    .build();
//
//            Event event4 = Event.builder()
//                    .id(UUID.randomUUID())
//                    .version(1)
//                    .eventType(EventType.DIAPER)
//                    .startDate(LocalDateTime.now().minusHours(2).minusMinutes(1))
//                    .endDate(LocalDateTime.now().minusHours(2))
//                    .baby(babyRepository.findBabyByUserAccount_Email(EMAIL).get(0))
//                    .notes("Baby had a lot of poop")
//                    .status(Status.COMPLETED)
//                    .build();
//
//            Event event5 = Event.builder()
//                    .id(UUID.randomUUID())
//                    .version(1)
//                    .eventType(EventType.CRYING)
//                    .startDate(LocalDateTime.now().minusHours(3))
//                    .endDate(LocalDateTime.now().minusHours(3).minusMinutes(20))
//                    .baby(babyRepository.findBabyByUserAccount_Email(EMAIL).get(0))
//                    .notes("Baby was hungry")
//                    .status(Status.COMPLETED)
//                    .build();
//
//            eventRepository.save(event1);
//            eventRepository.save(event2);
//            eventRepository.save(event3);
//            eventRepository.save(event4);
//            eventRepository.save(event5);
//        }
//    }
}
