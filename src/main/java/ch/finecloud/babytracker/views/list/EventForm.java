package ch.finecloud.babytracker.views.list;

import ch.finecloud.babytracker.data.entity.Baby;
import ch.finecloud.babytracker.data.entity.Event;
import ch.finecloud.babytracker.data.entity.EventType;
import ch.finecloud.babytracker.data.entity.Status;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.time.Duration;
import java.util.List;

public class EventForm extends FormLayout {
    ComboBox<EventType> eventType = new ComboBox<>("EventType");
    ComboBox<Baby> baby = new ComboBox<>("Baby");
    DateTimePicker startDate = new DateTimePicker();
    DateTimePicker endDate = new DateTimePicker();
    TextField notes = new TextField("Notes");
    ComboBox<Status> status = new ComboBox<>("Status");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    // Other fields omitted
    Binder<Event> binder = new BeanValidationBinder<>(Event.class);

    public EventForm(List<Baby> babies) {
        addClassName("event-form");
        binder.bindInstanceFields(this);
        startDate.setLabel("Start date and time");
        startDate.setStep(Duration.ofMinutes(15));
        endDate.setLabel("End date and time");
        endDate.setStep(Duration.ofMinutes(15));
        eventType.setItems(EventType.values());
        eventType.setItemLabelGenerator(EventType::getDisplayName);
        baby.setItems(babies);
        baby.setItemLabelGenerator(Baby::getName);
        status.setItems(Status.values());
        status.setItemLabelGenerator(Status::getDisplayName);

        add(baby,
                eventType,
                startDate,
                endDate,
                notes,
                status,
                createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave()); // <1>
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean()))); // <2>
        close.addClickListener(event -> fireEvent(new CloseEvent(this))); // <3>

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid())); // <4>
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean())); // <6>
        }
    }


    public void setEvent(Event event) {
        binder.setBean(event); // <1>
    }

    // Events
    public static abstract class BabyFormEvent extends ComponentEvent<EventForm> {
        private Event event;

        protected BabyFormEvent(EventForm source, Event event) {
            super(source, false);
            this.event = event;
        }

        public Event getEvent() {
            return event;
        }
    }

    public static class SaveEvent extends BabyFormEvent {
        SaveEvent(EventForm source, Event event) {
            super(source, event);
        }
    }

    public static class DeleteEvent extends BabyFormEvent {
        DeleteEvent(EventForm source, Event event) {
            super(source, event);
        }

    }

    public static class CloseEvent extends BabyFormEvent {
        CloseEvent(EventForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }


}

