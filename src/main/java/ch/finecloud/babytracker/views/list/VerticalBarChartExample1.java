package ch.finecloud.babytracker.views.list;

import ch.finecloud.babytracker.data.dto.EventTypeNumber;
import ch.finecloud.babytracker.data.entity.EventType;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.helper.Series;

import java.util.List;


public class VerticalBarChartExample1 extends ApexChartsBuilder {

    public VerticalBarChartExample1(List<EventTypeNumber> numberOfEventsPerEventType) {

        List<String> eventTypes = numberOfEventsPerEventType.stream()
                .map(EventTypeNumber::eventType)
                .map(EventType::getDisplayName)
                .toList();

        List<String> numberOfEvents = numberOfEventsPerEventType.stream()
                .map(EventTypeNumber::numberOfEvents)
                .map(String::valueOf)
                .toList();

        withChart(ChartBuilder.get()
                .withType(Type.BAR)
                .build())
                .withPlotOptions(PlotOptionsBuilder.get()
                        .withBar(BarBuilder.get()
                                .withHorizontal(false)
                                .withColumnWidth("55%")
                                .build())
                        .build())
                .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(false).build())
                .withStroke(StrokeBuilder.get()
                        .withShow(true)
                        .withWidth(2.0)
                        .withColors("transparent")
                        .build())
//                .withSeries(new Series<>("Net Profit", "44", "55", "57", "56", "61", "58", "63", "60", "66"),
//                        new Series<>("Revenue", "76", "85", "101", "98", "87", "105", "91", "114", "94"),
//                        new Series<>("Free Cash Flow", "35", "41", "36", "26", "45", "48", "52", "53", "41"))
                .withSeries(new Series<>("Number of Events", numberOfEvents.toArray(new String[0])))
                .withXaxis(XAxisBuilder.get().withCategories(eventTypes).build())
                .withFill(FillBuilder.get()
                        .withOpacity(1.0).build());
    }
}

