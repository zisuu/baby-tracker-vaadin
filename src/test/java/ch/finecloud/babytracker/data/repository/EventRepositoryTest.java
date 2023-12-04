package ch.finecloud.babytracker.data.repository;

import ch.finecloud.babytracker.TestBabyTrackerApplication;
import ch.finecloud.babytracker.bootstrap.BootstrapData;
import ch.finecloud.babytracker.config.TestConfig;
import ch.finecloud.babytracker.data.dto.EventTypeNumber;
import ch.finecloud.babytracker.data.entity.Event;
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
class EventRepositoryTest {

    @Autowired
    private EventRepository eventRepository;

    private Event event;

    @BeforeEach
    void setUp() {
        event = getRandomEvent();
    }

    private Event getRandomEvent() {
        long count = eventRepository.count();
        assertThat(count).isPositive();
        int randomIndex = new Random().nextInt((int) count);
        Slice<Event> eventSlice = eventRepository.findAll(PageRequest.of(randomIndex, 1));
        return eventSlice.getContent().get(0);
    }

    @Test
    void numberOfEventsPerEventType() {
        List<EventTypeNumber> eventTypeNumbers = eventRepository.numberOfEventsPerEventType(event.getBaby().getUserAccount().getEmail());
        assertThat(eventTypeNumbers).isNotNull();
    }

}