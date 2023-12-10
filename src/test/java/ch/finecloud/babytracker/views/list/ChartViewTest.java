package ch.finecloud.babytracker.views.list;

import ch.finecloud.babytracker.data.dto.BabySleepPerDay;
import ch.finecloud.babytracker.data.dto.EventTypeNumber;
import ch.finecloud.babytracker.data.entity.Role;
import ch.finecloud.babytracker.views.chart.VerticalBarChart;
import com.vaadin.flow.component.UI;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ChartViewTest extends KaribuTest {


    @Test
    void testNumberOfEventsPerEventType() {
        login("userAccount1@example.com", "password1", List.of(Role.USER.name()));
        Optional<ChartView> chartView = UI.getCurrent().navigate(ChartView.class);
        List<EventTypeNumber> numberOfEventsPerEventType = chartView.get().numberOfEventsPerEventType;
        assertThat(numberOfEventsPerEventType).isNotNull();
    }

    @Test
    void testIfSleepCharCanBeDisplayed() {
        login("userAccount1@example.com", "password1", List.of(Role.USER.name()));
        Optional<ChartView> chartView = UI.getCurrent().navigate(ChartView.class);
        List<BabySleepPerDay> sleepPerNight = chartView.get().sleepPerNight;
        assertThat(sleepPerNight).isNotNull();
    }

    @Test
    void testIfChart1CanBeDisplayed() {
        login("userAccount1@example.com", "password1", List.of(Role.USER.name()));
        Optional<ChartView> chartView = UI.getCurrent().navigate(ChartView.class);
        VerticalBarChartExample1 verticalBarChartExample1 = chartView.get().verticalBarChartExample1;
        assertThat(verticalBarChartExample1).isNotNull();
    }

    @Test
    void testIfChart2CanBeDisplayed() {
        login("userAccount1@example.com", "password1", List.of(Role.USER.name()));
        Optional<ChartView> chartView = UI.getCurrent().navigate(ChartView.class);
        VerticalBarChart verticalBarChart = chartView.get().verticalBarChart;
        assertThat(verticalBarChart).isNotNull();
    }
}

