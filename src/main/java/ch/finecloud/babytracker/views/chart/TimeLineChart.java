package ch.finecloud.babytracker.views.chart;

import ch.finecloud.babytracker.data.entity.Event;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.config.xaxis.XAxisType;
import com.github.appreciated.apexcharts.helper.DateCoordinate;
import com.github.appreciated.apexcharts.helper.Series;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class TimeLineChart extends ApexChartsBuilder {
    public TimeLineChart(List<Event> allEvents) {
        List<DateCoordinate> dateCoordinates = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime last24Hours = now.minusHours(24);

        for (Event event : allEvents) {
            LocalDateTime startDate = event.getStartDate();
            LocalDateTime endDate = event.getEndDate();

            // Skip events with NULL end_date
            if (endDate == null) {
                continue;
            }

            // Only include events within the last 24 hours
            if (startDate.isAfter(last24Hours) || endDate.isAfter(last24Hours)) {
                String eventName = event.getEventType().name(); // You can use a more appropriate field here

                DateCoordinate dateCoordinate = new DateCoordinate(
                        eventName,
                        startDate.toInstant(ZoneOffset.UTC).toEpochMilli(),
                        endDate.toInstant(ZoneOffset.UTC).toEpochMilli()
                );

                dateCoordinates.add(dateCoordinate);
            }
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
                        .withMin(last24Hours.toInstant(ZoneOffset.UTC).toEpochMilli())
                        .withMax(now.toInstant(ZoneOffset.UTC).toEpochMilli())
                        .build())
                .withXaxis(XAxisBuilder.get()
                        .withType(XAxisType.DATETIME)
                        .build());
    }
}
