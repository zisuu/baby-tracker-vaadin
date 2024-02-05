package ch.finecloud.babytracker.views.list;

import ch.finecloud.babytracker.data.entity.Event;
import ch.finecloud.babytracker.data.entity.EventType;
import ch.finecloud.babytracker.data.entity.Status;
import ch.finecloud.babytracker.data.service.BabyTrackerService;
import ch.finecloud.babytracker.views.MainLayout;
import com.flowingcode.vaadin.addons.fontawesome.FontAwesome;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SpringComponent
@Scope("prototype")
@PermitAll
@PageTitle("Grid with Filters")
@Route(value = "grid-with-filters", layout = MainLayout.class)
public class GridwithFiltersView extends Div {

    public static final String START_DATE = "startDate";
    private Grid<Event> grid;

    private Filters filters;
    private final BabyTrackerService babyTrackerService;

    public GridwithFiltersView(BabyTrackerService babyTrackerService) {
        this.babyTrackerService = babyTrackerService;
        setSizeFull();
        addClassNames("gridwith-filters-view");

        filters = new Filters(() -> refreshGrid());
        VerticalLayout layout = new VerticalLayout(createMobileFilters(), filters, createGrid());
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        add(layout);
    }

    private HorizontalLayout createMobileFilters() {
        // Mobile version
        HorizontalLayout mobileFilters = new HorizontalLayout();
        mobileFilters.setWidthFull();
        mobileFilters.addClassNames(LumoUtility.Padding.MEDIUM, LumoUtility.BoxSizing.BORDER,
                LumoUtility.AlignItems.CENTER);
        mobileFilters.addClassName("mobile-filters");

        Icon mobileIcon = new Icon("lumo", "plus");
        Span filtersHeading = new Span("Filters");
        mobileFilters.add(mobileIcon, filtersHeading);
        mobileFilters.setFlexGrow(1, filtersHeading);
        filters.setVisible(false);
        mobileFilters.addClickListener(e -> {
            if (filters.getClassNames().contains("visible")) {
                filters.removeClassName("visible");
                filters.setVisible(false);
                mobileIcon.getElement().setAttribute("icon", "lumo:plus");
            } else {
                filters.addClassName("visible");
                filters.setVisible(true);
                mobileIcon.getElement().setAttribute("icon", "lumo:minus");
            }
        });
        return mobileFilters;
    }

    public static class Filters extends Div implements Specification<Event> {

        private final TextField notes = new TextField("Notes");
        private final DatePicker startDate = new DatePicker("Start date");
        private final DatePicker endDate = new DatePicker();
        private final MultiSelectComboBox<String> eventTypes = new MultiSelectComboBox<>("EventTypes");
        private final CheckboxGroup<String> status = new CheckboxGroup<>("Status");

        public Filters(Runnable onSearch) {

            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);
            notes.setPlaceholder("Filter by Notes ...");

            eventTypes.setItems(Stream.of(EventType.values()).map(EventType::getDisplayName).toList());

            status.setItems(Stream.of(Status.values()).map(Status::getDisplayName).toList());
            status.addClassName("double-width");

            // Action buttons
            Button resetBtn = new Button("Reset");
            resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetBtn.addClickListener(e -> {
                notes.clear();
                startDate.clear();
                endDate.clear();
                eventTypes.clear();
                status.clear();
                onSearch.run();
            });
            Button searchBtn = new Button("Search");
            searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            searchBtn.addClickListener(e -> onSearch.run());

            Div actions = new Div(resetBtn, searchBtn);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions");

            add(notes, createDateRangeFilter(), eventTypes, status, actions);
        }

        private Component createDateRangeFilter() {
            startDate.setPlaceholder("From");

            endDate.setPlaceholder("To");

            // For screen readers
            startDate.setAriaLabel("From date");
            endDate.setAriaLabel("To date");

            FlexLayout dateRangeComponent = new FlexLayout(startDate, new Text(" – "), endDate);
            dateRangeComponent.setAlignItems(FlexComponent.Alignment.BASELINE);
            dateRangeComponent.addClassName(LumoUtility.Gap.XSMALL);

            return dateRangeComponent;
        }

        @Override
        public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();
            if (!notes.isEmpty()) {
                String lowerCaseFilter = notes.getValue().toLowerCase();
                Predicate notesMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("notes")),
                        "%" + lowerCaseFilter + "%");
                predicates.add(notesMatch);
            }
            if (startDate.getValue() != null) {
                String databaseColumn = START_DATE;
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(databaseColumn),
                        criteriaBuilder.literal(startDate.getValue())));
            }
            if (endDate.getValue() != null) {
                String databaseColumn = "endDate";
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.literal(endDate.getValue()),
                        root.get(databaseColumn)));
            }
            if (!eventTypes.isEmpty()) {
                String databaseColumn = "eventType";
                List<Predicate> eventTypesPredicates = new ArrayList<>();
                for (String eventType : eventTypes.getValue()) {
                    eventTypesPredicates
                            .add(criteriaBuilder.equal(criteriaBuilder.literal(eventType.toUpperCase()), root.get(databaseColumn)));
                }
                predicates.add(criteriaBuilder.or(eventTypesPredicates.toArray(Predicate[]::new)));
            }
            if (!status.isEmpty()) {
                String databaseColumn = "status";
                List<Predicate> statusPredicates = new ArrayList<>();
                for (String status : status.getValue()) {
                    statusPredicates.add(criteriaBuilder.equal(criteriaBuilder.literal(status.toUpperCase()), root.get(databaseColumn)));
                }
                predicates.add(criteriaBuilder.or(statusPredicates.toArray(Predicate[]::new)));
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        }
    }

    private Component createGrid() {
        grid = new Grid<>(Event.class, false);
        grid.addColumn(event -> event.getBaby().getName()).setHeader("Baby").setAutoWidth(true);
        grid.addComponentColumn(event -> generateIcon(event.getEventType().getIconName())).setHeader("Type")
                .setAutoWidth(true).setFlexGrow(0);
        grid.addColumn("startDate").setAutoWidth(true);
        grid.addColumn("endDate").setAutoWidth(true);
        grid.addColumn("notes").setAutoWidth(true);
        grid.addColumn(createStatusComponentRenderer()).setHeader("Status")
                .setAutoWidth(true);
        grid.setItems(query -> babyTrackerService.list(filters,
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query))).stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);
        // Set initial sorting by startDate in descending order
        List<GridSortOrder<Event>> sortOrders = new ArrayList<>();
        sortOrders.add(new GridSortOrder<>(grid.getColumnByKey("startDate"), SortDirection.DESCENDING));
        grid.sort(sortOrders);

        return grid;
    }

    private static ComponentRenderer<Span, Event> createStatusComponentRenderer() {
        return new ComponentRenderer<>(Span::new, statusComponentUpdater);
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

    private Icon generateIcon(String iconName) {
        FontAwesome.Solid icon = FontAwesome.Solid.valueOf(iconName);
        return icon.create();
    }

    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }
}
