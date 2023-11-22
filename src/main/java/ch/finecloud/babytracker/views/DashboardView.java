package ch.finecloud.babytracker.views;

import ch.finecloud.babytracker.data.service.BabyTrackerService;
import ch.finecloud.babytracker.security.SecurityService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

@PermitAll
@Route(value = "", layout = MainLayout.class) // <1>
@PageTitle("Dashboard | Baby Tracker")
public class DashboardView extends VerticalLayout {
    private final BabyTrackerService service;

    public DashboardView(BabyTrackerService service) { // <2>
        this.service = service;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER); // <3>

        add(getEventStats());

        add(new Button("Sleeping"));
        add(new Button("Crying"));
        add(new Button("Breastfeeding"));
    }

    private Component getEventStats() {
        Span stats = new Span("You've added " + service.countEvents() + " events so far"); // <4>
        stats.addClassNames(
            LumoUtility.FontSize.XLARGE,
            LumoUtility.Margin.Top.MEDIUM);
        return stats;
    }
}