package ch.finecloud.babytracker.views.chart;

import ch.finecloud.babytracker.data.entity.Event;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.config.xaxis.XAxisType;
import com.github.appreciated.apexcharts.helper.DateCoordinate;
import com.github.appreciated.apexcharts.helper.Series;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimeLineChart extends ApexChartsBuilder {
    public TimeLineChart(List<Event> allEvents) {
        List<DateCoordinate> dateCoordinates = new ArrayList<>();
        // filter all events out that have null endDate or startDate
        allEvents.removeIf(event -> event.getEndDate() == null || event.getStartDate() == null);

        for (Event event : allEvents) {
            LocalDateTime startDate = event.getStartDate();
            LocalDateTime endDate = event.getEndDate();
            String eventName = event.getEventType().name(); // You can use a more appropriate field here

            DateCoordinate dateCoordinate = new DateCoordinate(eventName, startDate.toLocalDate(), endDate.toLocalDate());
            dateCoordinates.add(dateCoordinate);
        }

        Series<DateCoordinate> series = new Series<>(dateCoordinates.toArray(new DateCoordinate[0]));

        withChart(ChartBuilder.get()
                .withType(Type.RANGEBAR)
                .build())
                .withPlotOptions(PlotOptionsBuilder.get()
                        .withBar(BarBuilder.get()
                                .withRangeBarGroupRows(true)
                                .withHorizontal(true)
                                .build())
                        .build())
                .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(false)
                        .build())
                .withSeries(series)
                .withYaxis(YAxisBuilder.get()
                        .withMin(LocalDate.now().minusDays(30)) // You may need to calculate the appropriate min and max
                        .withMax(LocalDate.now())
                        .build())
                .withXaxis(XAxisBuilder.get()
                        .withType(XAxisType.DATETIME)
                        .build());
    }
}