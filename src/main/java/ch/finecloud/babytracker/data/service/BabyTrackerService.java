package ch.finecloud.babytracker.data.service;

import ch.finecloud.babytracker.data.entity.Event;
import ch.finecloud.babytracker.data.entity.Baby;
import ch.finecloud.babytracker.data.repository.BabyRepository;
import ch.finecloud.babytracker.data.repository.EventRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.security.Principal;
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

//    @PreAuthorize("hasRole('ADMIN')")
//    public List<Event> findAllEvents(String stringFilter) {
//        if (stringFilter == null || stringFilter.isEmpty()) {
//            return eventRepository.findAll();
//        } else {
//            return eventRepository.search(stringFilter);
//        }
//    }

    @PreAuthorize("hasRole('USER')")
    public List<Event> findAllEventsByUserAccountEmail(String stringFilter, String email) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return eventRepository.findEventsByBaby_UserAccount_Email(email);
        } else {
            return eventRepository.searchEventsByBaby_UserAccount_Email(stringFilter, email);
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

//    public List<Baby> findAllBabies(String stringFilter) {
//        if (stringFilter == null || stringFilter.isEmpty()) {
//            return babyRepository.findAll();
//        } else {
//            return babyRepository.search(stringFilter);
//        }
//    }

    @PreAuthorize("hasRole('USER')")
    public List<Baby> findBabyByUserAccount_Email(String stringFilter, String email) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return babyRepository.findBabyByUserAccount_Email(email);
        } else {
            return babyRepository.searchBabiesByUserAccount_Email(stringFilter, email);
        }
    }

    public void saveBaby(Baby baby) {
        if (baby == null) {
            System.err.println("Baby is null. Are you sure you have connected your form to the application?");
            return;
        }
        babyRepository.save(baby);
    }

    public void deleteBaby(Baby baby) {
        babyRepository.delete(baby);
    }
}