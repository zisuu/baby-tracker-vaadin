//package ch.finecloud.babytracker.views.list;
//
//import ch.finecloud.babytracker.data.entity.Event;
//import com.github.appreciated.apexcharts.ApexChartsBuilder;
//import com.github.appreciated.apexcharts.config.builder.*;
//import com.github.appreciated.apexcharts.config.chart.Type;
//import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
//import com.github.appreciated.apexcharts.config.xaxis.XAxisType;
//import com.github.appreciated.apexcharts.helper.DateCoordinate;
//import com.github.appreciated.apexcharts.helper.Series;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TimeLineChartExamplev2 extends ApexChartsBuilder {
//    public TimeLineChartExamplev2(List<Event> allEvents) {
//        List<DateCoordinate> dateCoordinates = new ArrayList<>();
//
//        for (Event event : allEvents) {
//            LocalDateTime startDate = event.getStartDate();
//            LocalDateTime endDate = event.getEndDate();
//            String eventName = event.getEventType().name(); // You can use a more appropriate field here
//
//            DateCoordinate dateCoordinate = new DateCoordinate(eventName, startDate.toLocalDate(), endDate.toLocalDate());
//            dateCoordinates.add(dateCoordinate);
//        }
//
//        Series<DateCoordinate> series = new Series<>(dateCoordinates.toArray(new DateCoordinate[0]));
//
//        withChart(ChartBuilder.get()
//                .withType(Type.RANGEBAR)
//                .build())
//                .withPlotOptions(PlotOptionsBuilder.get()
//                        .withBar(BarBuilder.get()
//                                .withRangeBarGroupRows(true)
//                                .withHorizontal(true)
//                                .build())
//                        .build())
//                .withDataLabels(DataLabelsBuilder.get()
//                        .withEnabled(false)
//                        .build())
//                .withSeries(series)
//                .withYaxis(YAxisBuilder.get()
//                        .withMin(LocalDate.of(2023, 10, 1)) // You may need to calculate the appropriate min and max
//                        .withMax(LocalDate.of(2023, 12, 14))
//                        .build())
//                .withXaxis(XAxisBuilder.get()
//                        .withType(XAxisType.DATETIME)
//                        .build());
//    }
//}