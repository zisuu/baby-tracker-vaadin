package ch.finecloud.babytracker.views;

import ch.finecloud.babytracker.data.entity.*;
import ch.finecloud.babytracker.data.service.BabyTrackerService;
import com.flowingcode.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;


@PermitAll
@Route(value = "", layout = MainLayout.class)
@PageTitle("Dashboard | Baby Tracker")
public class DashboardView extends VerticalLayout {
    private final BabyTrackerService service;
    ComboBox<String> selectedBaby = new ComboBox<>("Select a baby");
    Button startSleepButton = new Button("Start Sleeping", FontAwesome.Solid.BED.create());
    Button stopAndSaveButton = new Button("Stop Sleeping", FontAwesome.Solid.BED.create());
    LocalDateTime startTime;
    Span elapsedTime = new Span();
    Duration initialDuration;

    public DashboardView(BabyTrackerService service) {
        this.service = service;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        List<String> babyByUserAccountEmail = service.findBabyByUserAccount_Email(null).stream().map(Baby::getName).toList();
        selectedBaby.setId("selectedBabyComboBox");
        selectedBaby.setItems(babyByUserAccountEmail);
        try {
            selectedBaby.setValue(babyByUserAccountEmail.getFirst());
        } catch (NoSuchElementException ex) {
            selectedBaby.setPlaceholder("No baby found");
        }

        selectedBaby.addValueChangeListener(event -> {
            if (event.getValue() != null) {
                updateStats(event.getValue());
            }
        });
        updateStats(selectedBaby.getValue());


        startSleepButton.addClickListener(event -> {
            remove(startSleepButton);
            add(stopAndSaveButton);
            elapsedTime.setVisible(true);
            startTime = LocalDateTime.now(ZoneId.of("Europe/Zurich"));

            // Create and save the event in the backend
            service.saveEvent(new Event(
                    null,
                    0,
                    EventType.SLEEPING,
                    startTime,
                    null,
                    null,
                    service.findBabyByUserAccount_Email(selectedBaby.getValue()).get(0),
                    Status.STARTED
            ));
            updateStats(selectedBaby.getValue());
        });
        stopAndSaveButton.addClickListener(event -> stopAndSaveEvent());
    }

    private Component getEventStats(String babyName) {
        Span stats = new Span("You've added " + service.countEventsByBaby(babyName) + " events for " + babyName + " so far"); // <4>
        stats.addClassNames(
                LumoUtility.FontSize.XLARGE,
                LumoUtility.Margin.Top.MEDIUM);
        return stats;
    }


    private void updateStats(String babyName) {
        // Clear existing components
        removeAll();

        // Re-add components with updated data
        selectedBaby.setValue(babyName);
        add(selectedBaby);
        add(getEventStats(babyName));

        // Retrieve the latest event for the selected baby
        Event latestEvent = service.findLatestEventByBaby(selectedBaby.getValue());

        if (latestEvent != null && latestEvent.getStatus() == Status.STARTED) {
            remove(startSleepButton);
            add(stopAndSaveButton);
            // There is an ongoing event, initialize start time and initial duration
            startTime = latestEvent.getStartDate();
            initialDuration = Duration.between(startTime, LocalDateTime.now());

            // Update stats with initial duration
            if (initialDuration != null) {
                String value = babyName + " is sleeping since " + initialDuration.toHours() +
                        " hours and " + (initialDuration.toMinutes() % 60) + " minutes and " + initialDuration.toSeconds() % 60 + " seconds";
                elapsedTime.add(value);
                add(elapsedTime);
            }

        } else {
            // No ongoing event, initialize start time to current time
            startTime = LocalDateTime.now();
            add(startSleepButton);
            remove(stopAndSaveButton);

        }
        add(elapsedTime);
    }

    private void stopAndSaveEvent() {
        add(startSleepButton);
        remove(stopAndSaveButton);
        // Update the event in the backend with the end date
        Event eventToUpdate = service.findLatestEventByBaby(selectedBaby.getValue());
        if (eventToUpdate != null) {
            eventToUpdate.setEndDate(LocalDateTime.now());
            eventToUpdate.setStatus(Status.COMPLETED);
            service.saveEvent(eventToUpdate);
            showSuccess();
            elapsedTime.setVisible(false);
            elapsedTime.removeAll();
        }
        updateStats(selectedBaby.getValue());
    }

    private void showSuccess() {
        Notification notification =
                Notification.show("Event saved!");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }
}