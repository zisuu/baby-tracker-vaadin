package ch.finecloud.babytracker.views.chart;

import ch.finecloud.babytracker.data.dto.EventTypeNumber;
import ch.finecloud.babytracker.data.entity.EventType;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.LegendBuilder;
import com.github.appreciated.apexcharts.config.builder.ResponsiveBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.responsive.builder.OptionsBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class DonutChart extends ApexChartsBuilder {
    public DonutChart(List<EventTypeNumber> numberOfEventsPerEventType) {

        // calculate the percentage of each event type
        List<Double> percentages = numberOfEventsPerEventType.stream()
                .map(EventTypeNumber::numberOfEvents)
                .mapToDouble(Long::doubleValue)
                .map(numberOfEvents -> numberOfEvents / numberOfEventsPerEventType.stream().map(EventTypeNumber::numberOfEvents).mapToDouble(Long::doubleValue).sum())
                .boxed()
                .collect(Collectors.toList());

        List<String> eventTypes = numberOfEventsPerEventType.stream()
                .map(EventTypeNumber::eventType)
                .map(EventType::getDisplayName)
                .toList();

        withChart(ChartBuilder.get().withType(Type.DONUT).build())
                .withLabels(eventTypes.toArray(new String[0]))
                .withLegend(LegendBuilder.get()
                        .withPosition(Position.RIGHT)
                        .build())
                .withSeries(percentages.toArray(new Double[0]))
                .withResponsive(ResponsiveBuilder.get()
                        .withBreakpoint(480.0)
                        .withOptions(OptionsBuilder.get()
                                .withLegend(LegendBuilder.get()
                                        .withPosition(Position.BOTTOM)
                                        .build())
                                .build())
                        .build());
    }
}