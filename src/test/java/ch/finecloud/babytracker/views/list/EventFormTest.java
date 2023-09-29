package ch.finecloud.babytracker.views.list;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import static org.junit.jupiter.api.Assertions.assertEquals;

import ch.finecloud.babytracker.data.entity.Baby;
import ch.finecloud.babytracker.data.entity.Event;
import ch.finecloud.babytracker.data.entity.EventType;
import ch.finecloud.babytracker.data.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventFormTest {
    private List<Baby> babies;
    private Event testEvent;
    private Baby baby1;
    private Baby baby2;
    private Status status1;
    private Status status2;

    @BeforeEach
    public void setupData() {
        babies = new ArrayList<>();
        baby1 = new Baby();
        baby1.setName("Baby Name1");
        baby2 = new Baby();
        baby2.setName("Baby Name2");
        babies.add(baby1);
        babies.add(baby2);

        testEvent = new Event();
        testEvent.setEventType(EventType.BATHING);
        testEvent.setBaby(baby1);
        testEvent.setNotes("Test Notes");
        testEvent.setStatus(Status.STARTED);
        testEvent.setStartDate(LocalDateTime.of(2021, 1, 1, 12, 0));
        testEvent.setEndDate(LocalDateTime.of(2021, 1, 1, 12, 30));
    }

    @Test
    public void formFieldsPopulated() {
        EventForm form = new EventForm(babies);
        form.setEvent(testEvent);
        assertEquals(EventType.BATHING, form.eventType.getValue());
        assertEquals(baby1, form.baby.getValue());
        assertEquals("Test Notes", form.notes.getValue());
        assertEquals(Status.STARTED, form.status.getValue());
        assertEquals(LocalDateTime.of(2021, 1, 1, 12, 0), form.startDate.getValue());
        assertEquals(LocalDateTime.of(2021, 1, 1, 12, 30), form.endDate.getValue());
    }

    @Test
    public void saveEventHasCorrectValues() {
        EventForm form = new EventForm(babies);
        Event event = new Event();
        form.setEvent(event);
        form.eventType.setValue(EventType.SLEEPING);
        form.baby.setValue(baby2);
        form.notes.setValue("Test Notes2");
        form.status.setValue(Status.INTERRUPTED);
        form.startDate.setValue(LocalDateTime.of(2023, 1, 1, 12, 0));
        form.endDate.setValue(LocalDateTime.of(2023, 1, 1, 12, 30));

        AtomicReference<Event> savedEventRef = new AtomicReference<>(null);
        form.addSaveListener(e -> {
            savedEventRef.set(e.getEvent());
        });
        form.save.click();
        Event savedEvent = savedEventRef.get();

        assertEquals(EventType.SLEEPING, savedEvent.getEventType());
        assertEquals(baby2, savedEvent.getBaby());
        assertEquals("Test Notes2", savedEvent.getNotes());
        assertEquals(Status.INTERRUPTED, savedEvent.getStatus());
        assertEquals(LocalDateTime.of(2023, 1, 1, 12, 0), savedEvent.getStartDate());
        assertEquals(LocalDateTime.of(2023, 1, 1, 12, 30), savedEvent.getEndDate());
    }
}