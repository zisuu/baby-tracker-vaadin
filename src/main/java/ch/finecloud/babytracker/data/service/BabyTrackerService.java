package ch.finecloud.babytracker.data.service;

import ch.finecloud.babytracker.data.entity.Event;
import ch.finecloud.babytracker.data.entity.Baby;
import ch.finecloud.babytracker.data.entity.Role;
import ch.finecloud.babytracker.data.entity.UserAccount;
import ch.finecloud.babytracker.data.repository.BabyRepository;
import ch.finecloud.babytracker.data.repository.EventRepository;
import ch.finecloud.babytracker.data.repository.UserAccountRepository;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BabyTrackerService {

    private final EventRepository eventRepository;
    private final BabyRepository babyRepository;
    private final UserAccountRepository userAccountRepository;

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

    @PreAuthorize("hasRole('USER')")
    public void deleteEvent(Event event, String email) {
        if (Objects.equals(event.getBaby().getUserAccount().getEmail(), email)) {
            eventRepository.delete(event);
        } else {
            System.err.println("UserAccount " + email + "is not allowed to delete this event.");
        }
    }

    @PreAuthorize("hasRole('USER')")
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

    @PreAuthorize("hasRole('USER')")
    public void saveBaby(Baby baby, String email) {
        if (baby == null) {
            System.err.println("Baby is null. Are you sure you have connected your form to the application?");
            return;
        }
        Optional<UserAccount> userAccount = userAccountRepository.findUserAccountByEmail(email);
        if (userAccount.isPresent()) {
            baby.setUserAccount(userAccount.get());
        } else {
            System.err.println("UserAccount is unknown. Are you sure you have connected your form to the application?");
            return;
        }
        babyRepository.save(baby);
    }

    @PreAuthorize("hasRole('USER')")
    public void deleteBaby(Baby baby, String email) {
        if (Objects.equals(baby.getUserAccount().getEmail(), email)) {
            babyRepository.delete(baby);
        } else {
            System.err.println("UserAccount " + email + "is not allowed to delete this baby.");
        }
    }

    public String addUserAccount(String email, String password) {
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail(email);
        userAccount.setPassword(password);
        userAccount.setRole(Role.USER);
        UserAccount savedUserAccount;
        try {
            savedUserAccount = userAccountRepository.save(userAccount);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
        return savedUserAccount.getEmail();
    }

    public boolean checkIfUserExists(String email) {
        return userAccountRepository.findUserAccountByEmail(email).isPresent();
    }
}