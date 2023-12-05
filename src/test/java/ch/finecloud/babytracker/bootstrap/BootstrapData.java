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
        loadCsvData();
    }

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
                        .baby(babyRepository.findBabyByName("Max").get())
                        .build());
            });

        }
    }
}
