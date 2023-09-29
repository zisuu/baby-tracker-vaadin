//package com.example.application.views.list;
//
//import entity.data.ch.finecloud.babytracker.Baby;
//import entity.data.ch.finecloud.babytracker.Event;
//import entity.data.ch.finecloud.babytracker.Status;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicReference;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//public class EventFormTest {
//    private List<Baby> babies;
//    private List<Status> statuses;
//    private Event marcUsher;
//    private Baby baby1;
//    private Baby baby2;
//    private Status status1;
//    private Status status2;
//
//    @BeforeEach
//    public void setupData() {
//        babies = new ArrayList<>();
//        baby1 = new Baby();
//        baby1.setName("Vaadin Ltd");
//        baby2 = new Baby();
//        baby2.setName("IT Mill");
//        babies.add(baby1);
//        babies.add(baby2);
//
//        statuses = new ArrayList<>();
//        status1 = new Status();
//        status1.setName("Status 1");
//        status2 = new Status();
//        status2.setName("Status 2");
//        statuses.add(status1);
//        statuses.add(status2);
//
//        marcUsher = new Event();
//        marcUsher.setFirstName("Marc");
//        marcUsher.setLastName("Usher");
//        marcUsher.setEmail("marc@usher.com");
//        marcUsher.setStatus(status1);
//        marcUsher.setCompany(baby2);
//    }
//
//    @Test
//    public void formFieldsPopulated() {
//        EventForm form = new EventForm(babies, statuses);
//        form.setEvent(marcUsher);
//        assertEquals("Marc", form.firstName.getValue());
//        assertEquals("Usher", form.lastName.getValue());
//        assertEquals("marc@usher.com", form.email.getValue());
//        assertEquals(baby2, form.baby.getValue());
//        assertEquals(status1, form.status.getValue());
//    }
//
//    @Test
//    public void saveEventHasCorrectValues() {
//        EventForm form = new EventForm(babies, statuses);
//        Event event = new Event();
//        form.setEvent(event);
//        form.firstName.setValue("John");
//        form.lastName.setValue("Doe");
//        form.baby.setValue(baby1);
//        form.email.setValue("john@doe.com");
//        form.status.setValue(status2);
//
//        AtomicReference<Event> savedContactRef = new AtomicReference<>(null);
//        form.addSaveListener(e -> {
//            savedContactRef.set(e.getEvent());
//        });
//        form.save.click();
//        Event savedEvent = savedContactRef.get();
//
//        assertEquals("John", savedEvent.getFirstName());
//        assertEquals("Doe", savedEvent.getLastName());
//        assertEquals("john@doe.com", savedEvent.getEmail());
//        assertEquals(baby1, savedEvent.getCompany());
//        assertEquals(status2, savedEvent.getStatus());
//    }
//}