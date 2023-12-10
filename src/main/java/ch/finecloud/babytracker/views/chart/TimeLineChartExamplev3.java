package ch.finecloud.babytracker.views.chart;//package ch.finecloud.babytracker.views.list;
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
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//
//public class TimeLineChart extends ApexChartsBuilder {
//    public TimeLineChart(List<Event> allEvents) {
//        List<Series<DateCoordinate>> seriesList = new ArrayList<>();
//
//        for (Event event : allEvents) {
//            LocalDateTime startDate = event.getStartDate();
//            LocalDateTime endDate = event.getEndDate();
//
//            if (startDate != null && endDate != null) {
//                DateCoordinate dateCoordinate = new DateCoordinate<>(
//                        event.getEventType().toString(),
//                        startDate.toLocalDate(),
//                        endDate.toLocalDate()
//                );
//
//                seriesList.add(new Series<>(event.getBaby().getName(), dateCoordinate));
//            }
//        }
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
//                .withSeries(seriesList.toArray(new Series[0]))
//                .withYaxis(YAxisBuilder.get()
//                        .withMin(allEvents.stream().map(Event::getStartDate).min(Comparator.naturalOrder()).orElse(null))
//                        .withMax(allEvents.stream().map(Event::getEndDate).max(Comparator.naturalOrder()).orElse(null))
//                        .build())
//                .withXaxis(XAxisBuilder.get()
//                        .withType(XAxisType.DATETIME)
//                        .build());
//    }
//}
