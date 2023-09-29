package ch.finecloud.babytracker.data.service;

import ch.finecloud.babytracker.data.entity.Event;
import ch.finecloud.babytracker.data.entity.Baby;
import ch.finecloud.babytracker.data.repository.BabyRepository;
import ch.finecloud.babytracker.data.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service 
public class BabyTrackerService {

    private final EventRepository eventRepository;
    private final BabyRepository babyRepository;

    public BabyTrackerService(EventRepository eventRepository,
                              BabyRepository babyRepository) {
        this.eventRepository = eventRepository;
        this.babyRepository = babyRepository;
    }

    public List<Event> findAllEvents(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) { 
            return eventRepository.findAll();
        } else {
            return eventRepository.search(stringFilter);
        }
    }

    public long countEvents() {
        return eventRepository.count();
    }

    public void deleteEvent(Event event) {
        eventRepository.delete(event);
    }

    public void saveEvent(Event event) {
        if (event == null) {
            System.err.println("Event is null. Are you sure you have connected your form to the application?");
            return;
        }
        eventRepository.save(event);
    }

    public List<Baby> findAllBabies() {
        return babyRepository.findAll();
    }
}