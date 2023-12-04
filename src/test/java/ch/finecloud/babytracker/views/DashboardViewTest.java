package ch.finecloud.babytracker.views;

import ch.finecloud.babytracker.data.entity.Event;
import ch.finecloud.babytracker.data.entity.Role;
import ch.finecloud.babytracker.views.list.EventListView;
import ch.finecloud.babytracker.views.list.KaribuTest;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.github.mvysny.kaributesting.v10.LocatorJ._get;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DashboardViewTest extends KaribuTest {

    @Test
    void selectedBabyComboBoxPresent() {
        login("userAccount1@example.com", "password1", List.of(Role.USER.name()));

        Optional<DashboardView> dashboardView = UI.getCurrent().navigate(DashboardView.class);
        ComboBox<String> selectedBaby = _get(ComboBox.class, spec -> spec.withId("selectedBabyComboBox"));
        assertThat(selectedBaby).isNotNull();
    }
}