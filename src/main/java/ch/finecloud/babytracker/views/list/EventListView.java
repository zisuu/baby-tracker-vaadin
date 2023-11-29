package ch.finecloud.babytracker.views.list;

import ch.finecloud.babytracker.data.entity.Event;
import ch.finecloud.babytracker.data.entity.Status;
import ch.finecloud.babytracker.data.service.BabyTrackerService;
import ch.finecloud.babytracker.views.MainLayout;
import com.flowingcode.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;

import java.time.LocalDateTime;
import java.time.ZoneId;

@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "event-list-view", layout = MainLayout.class)
@PageTitle("Events | Baby Tracker")
public class EventListView extends VerticalLayout {
    Grid<Event> grid = new Grid<>(Event.class);
    TextField filterText = new TextField();
    EventForm form;
    BabyTrackerService babyTrackerService;

    public EventListView(BabyTrackerService babyTrackerService) {
        this.babyTrackerService = babyTrackerService;
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
        form = new EventForm(babyTrackerService.findBabyByUserAccount_Email(null));
        form.setWidth("25em");
        form.addSaveListener(this::saveEvent); // <1>
        form.addDeleteListener(this::deleteEvent); // <2>
        form.addCloseListener(e -> closeEditor()); // <3>
    }

    private void saveEvent(EventForm.SaveEvent event) {
        babyTrackerService.saveEvent(event.getEvent());
        updateList();
        closeEditor();
    }

    private void deleteEvent(EventForm.DeleteEvent event) {
        babyTrackerService.deleteEvent(event.getEvent());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.setId("eventGrid");
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(event -> event.getBaby().getName()).setHeader("Baby").setAutoWidth(true);
        grid.addComponentColumn(event -> generateIcon(event.getEventType().getIconName())).setHeader("Type")
                .setAutoWidth(true).setFlexGrow(0);
        grid.addColumn("startDate").setAutoWidth(true);
        grid.addColumn("endDate").setAutoWidth(true);
        grid.addColumn("notes").setAutoWidth(true);
        grid.addColumn(createStatusComponentRenderer()).setHeader("Status")
                .setAutoWidth(true);
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editEvent(event.getValue()));
    }

    private Icon generateIcon(String iconName) {
        FontAwesome.Solid icon = FontAwesome.Solid.valueOf(iconName);
        return icon.create();
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by Event Notes...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addEventButton = new Button("Add event");
        addEventButton.setId("add-button");
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
        Event newEvent = new Event();
        if (!babyTrackerService.findBabyByUserAccount_Email(null).isEmpty()) {
            newEvent.setBaby(babyTrackerService.findBabyByUserAccount_Email(null).get(0));
        }
        newEvent.setStartDate(LocalDateTime.now(ZoneId.systemDefault()));
        editEvent(newEvent);
    }


    private void updateList() {
        grid.setItems(babyTrackerService.findAllEventsByUserAccountEmail(filterText.getValue()));
    }

    private static final SerializableBiConsumer<Span, Event> statusComponentUpdater = (
            span, event) -> {
        String theme = "";
        if (event.getStatus() == null) {
            event.setStatus(Status.COMPLETED);
        }
        theme = switch (event.getStatus()) {
            case COMPLETED -> "badge success";
            case INTERRUPTED -> "badge error";
            default -> "badge";
        };
        span.getElement().setAttribute("theme", theme);
        span.setText(event.getStatus().getDisplayName());
    };

    private static ComponentRenderer<Span, Event> createStatusComponentRenderer() {
        return new ComponentRenderer<>(Span::new, statusComponentUpdater);
    }
}