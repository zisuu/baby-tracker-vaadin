//package ch.finecloud.babytracker.views;
//
//import ch.finecloud.babytracker.data.service.BabyTrackerService;
//import com.vaadin.flow.component.Component;
//import com.vaadin.flow.component.charts.Chart;
//import com.vaadin.flow.component.charts.model.ChartType;
//import com.vaadin.flow.component.charts.model.DataSeries;
//import com.vaadin.flow.component.charts.model.DataSeriesItem;
//import com.vaadin.flow.component.html.Span;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.router.PageTitle;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.theme.lumo.LumoUtility;
//import jakarta.annotation.security.PermitAll;
//
//@PermitAll
//@Route(value = "dashboard", layout = MainLayout.class) // <1>
//@PageTitle("Dashboard | Baby Tracker")
//public class DashboardView extends VerticalLayout {
//    private final BabyTrackerService service;
//
//    public DashboardView(BabyTrackerService service) { // <2>
//        this.service = service;
//        addClassName("dashboard-view");
//        setDefaultHorizontalComponentAlignment(Alignment.CENTER); // <3>
//
//        add(getEventStats(), getBabiesChart());
//    }
//
//    private Component getEventStats() {
//        Span stats = new Span(service.countEvents() + " events"); // <4>
//        stats.addClassNames(
//            LumoUtility.FontSize.XLARGE,
//            LumoUtility.Margin.Top.MEDIUM);
//        return stats;
//    }
//
//    private Chart getBabiesChart() {
//        Chart chart = new Chart(ChartType.PIE);
//
//        DataSeries dataSeries = new DataSeries();
//        service.findAllBabies().forEach(baby ->
//            dataSeries.add(new DataSeriesItem(baby.getName(), baby.getEventCount()))); // <5>
//        chart.getConfiguration().setSeries(dataSeries);
//        return chart;
//    }
//}