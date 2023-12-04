package ch.finecloud.babytracker.views.list;

import ch.finecloud.babytracker.data.dto.BabySleepPerDay;
import ch.finecloud.babytracker.data.dto.EventTypeNumber;
import ch.finecloud.babytracker.data.service.BabyTrackerService;
import ch.finecloud.babytracker.views.MainLayout;
import com.github.appreciated.apexcharts.ApexCharts;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;

import java.util.List;

@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "charts", layout = MainLayout.class)
@PageTitle("Charts | Baby Tracker")
public class ChartView extends VerticalLayout {
    BabyTrackerService babyTrackerService;

    List<EventTypeNumber> numberOfEventsPerEventType;
    List<BabySleepPerDay> sleepPerNight;
    VerticalBarChartExample1 verticalBarChartExample1;
    VerticalBarChartExample2 verticalBarChartExample2;

    public ChartView(BabyTrackerService babyTrackerService) {
        this.babyTrackerService = babyTrackerService;

        numberOfEventsPerEventType = getNumberOfEventsPerEventType();
        verticalBarChartExample1 = new VerticalBarChartExample1(numberOfEventsPerEventType);
        ApexCharts chart1 = verticalBarChartExample1.build();
        chart1.setId("chart1");
        add(new H2("Number of Events per EventType"), chart1);

        sleepPerNight = getSleepPerNight();
        verticalBarChartExample2 = new VerticalBarChartExample2(sleepPerNight);
        ApexCharts chart2 = verticalBarChartExample2.build();
        chart2.setId("chart2");
        add(new H2("Hours of Sleep per Night"), chart2);
    }

    private List<EventTypeNumber> getNumberOfEventsPerEventType() {
        return babyTrackerService.numberOfEventsPerEventType();
    }

    private List<BabySleepPerDay> getSleepPerNight() {
        return babyTrackerService.findBabySleepPerDay();
    }


}