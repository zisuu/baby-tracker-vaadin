package ch.finecloud.babytracker.views.list;

import ch.finecloud.babytracker.data.entity.Event;
import ch.finecloud.babytracker.data.entity.Status;
import ch.finecloud.babytracker.data.service.BabyTrackerService;
import ch.finecloud.babytracker.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;

import java.security.Principal;

@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "", layout = MainLayout.class)
@PageTitle("Events | Baby Tracker")
public class EventListView extends VerticalLayout {
    private final AuthenticationContext authenticationContext;
    Grid<Event> grid = new Grid<>(Event.class);
    TextField filterText = new TextField();
    EventForm form;
    BabyTrackerService service;

    public EventListView(BabyTrackerService service, AuthenticationContext authenticationContext) {
        this.service = service;
        this.authenticationContext = authenticationContext;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new EventForm(service.findBabyByUserAccount_Email(null, getEmail()));
        form.setWidth("25em");
        form.addSaveListener(this::saveEvent); // <1>
        form.addDeleteListener(this::deleteEvent); // <2>
        form.addCloseListener(e -> closeEditor()); // <3>
    }

    private void saveEvent(EventForm.SaveEvent event) {
        service.saveEvent(event.getEvent());
        updateList();
        closeEditor();
    }

    private void deleteEvent(EventForm.DeleteEvent event) {
        service.deleteEvent(event.getEvent());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(event -> event.getBaby().getName()).setHeader("Baby");
        grid.addColumn(event -> event.getEventType().getDisplayName()).setHeader("Event Type");
        grid.addColumn("startDate");
        grid.addColumn("endDate");
        grid.addColumn("notes");
        grid.addColumn(event -> {
            Status status = event.getStatus();
            return status != null ? status.getDisplayName() : "";
        }).setHeader("Status");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editEvent(event.getValue()));
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by Event Notes...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addEventButton = new Button("Add event");
        addEventButton.addClickListener(click -> addEvent());

        var toolbar = new HorizontalLayout(filterText, addEventButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editEvent(Event event) {
        if (event == null) {
            closeEditor();
        } else {
            form.setEvent(event);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setEvent(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addEvent() {
        grid.asSingleSelect().clear();
        editEvent(new Event());
    }


    private void updateList() {
        grid.setItems(service.findAllEventsByUserAccountEmail(filterText.getValue(), getEmail()));
    }

    private String getEmail() {
        return authenticationContext.getPrincipalName().isPresent() ? authenticationContext.getPrincipalName().get() : "";
    }
}