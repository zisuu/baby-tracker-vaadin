package ch.finecloud.babytracker.views.list;

import ch.finecloud.babytracker.data.dto.BabySleepPerDay;
import ch.finecloud.babytracker.data.dto.EventTypeNumber;
import ch.finecloud.babytracker.data.service.BabyTrackerService;
import ch.finecloud.babytracker.views.MainLayout;
import ch.finecloud.babytracker.views.chart.AreaChartExample;
import ch.finecloud.babytracker.views.chart.DonutChart;
import ch.finecloud.babytracker.views.chart.TimeLineChart;
import ch.finecloud.babytracker.views.chart.VerticalBarChart;
import com.github.appreciated.apexcharts.ApexCharts;
import com.vaadin.flow.component.html.H2;
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
    DonutChart donutChart;
    VerticalBarChart verticalBarChart;
    TimeLineChart timeLineChart;
    AreaChartExample areaChartExample;

    public ChartView(BabyTrackerService babyTrackerService) {
        this.babyTrackerService = babyTrackerService;

        numberOfEventsPerEventType = getNumberOfEventsPerEventType();
        donutChart = new DonutChart(numberOfEventsPerEventType);
        ApexCharts numberOfEventsPerEventTypeChart = donutChart.build();
        numberOfEventsPerEventTypeChart.setId("numberOfEventsPerEventTypeChart");
        add(new H2("Number of Events per EventType"), numberOfEventsPerEventTypeChart);

        sleepPerNight = getSleepPerNight();
        verticalBarChart = new VerticalBarChart(sleepPerNight);
        ApexCharts sleepPerNightChart = verticalBarChart.build();
        sleepPerNightChart.setId("getSleepPerNightChart");
        add(new H2("Hours of sleep"), sleepPerNightChart);

        timeLineChart = new TimeLineChart(babyTrackerService.findAllEventsByUserAccountEmail(null));
        ApexCharts chart3 = timeLineChart.build();
        chart3.setId("timeLineChart");
        add(new H2("TimeLine"), chart3);

        areaChartExample = new AreaChartExample();
        ApexCharts chart4 = areaChartExample.build();
        chart4.setId("chart4");
        add(new H2("Area Chart Sleep"), chart4);
    }

    private List<EventTypeNumber> getNumberOfEventsPerEventType() {
        return babyTrackerService.numberOfEventsPerEventType();
    }

    private List<BabySleepPerDay> getSleepPerNight() {
        return babyTrackerService.findBabySleepPerDay();
    }


}