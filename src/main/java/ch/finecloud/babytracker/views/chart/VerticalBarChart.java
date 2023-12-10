package ch.finecloud.babytracker.views.chart;

import ch.finecloud.babytracker.data.dto.BabySleepPerDay;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.helper.Series;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;


public class VerticalBarChart extends ApexChartsBuilder {

    public VerticalBarChart(List<BabySleepPerDay> sleepPerDays) {

        // Filter out entries with null endDate
        List<BabySleepPerDay> validSleepPerDays = sleepPerDays.stream()
                .filter(day -> day.sleepDuration() != null)
                .collect(Collectors.toList());

        List<String> days = validSleepPerDays.stream()
                .map(BabySleepPerDay::day)
                .map(Date::toString) // or format it as you prefer
                .toList();

        List<Long> totalSleepDurationsInSeconds = validSleepPerDays.stream()
                .map(BabySleepPerDay::sleepDuration)
                .toList();

        List<Integer> sleepDurationHours = totalSleepDurationsInSeconds.stream()
                .map(seconds -> Math.toIntExact(seconds / 3600))
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
//                .withSeries(new Series<>("Sleep Duration", sleepDurations.toArray(new Long[0])))
                .withSeries(new Series<>("Sleep Duration Hours", sleepDurationHours.toArray(new Integer[0])))
                .withXaxis(XAxisBuilder.get().withCategories(days).build())
                .withFill(FillBuilder.get()
                        .withOpacity(1.0).build());
    }
}

