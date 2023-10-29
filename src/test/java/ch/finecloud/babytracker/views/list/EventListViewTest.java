package ch.finecloud.babytracker.views.list;

import static com.github.mvysny.kaributesting.v10.LocatorJ._get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.finecloud.babytracker.data.entity.Event;
import ch.finecloud.babytracker.data.entity.Role;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class EventListViewTest extends KaribuTest {


    @Test
    void formShownWhenContactSelected() {
        login("userAccount1@example.com", "password1", List.of(Role.USER.name()));

        Optional<EventListView> eventListView = UI.getCurrent().navigate(EventListView.class);
        Grid<Event> grid = _get(Grid.class, spec -> spec.withId("eventGrid"));
        Event firstEvent = getFirstItem(grid);

        EventForm form = eventListView.get().form;

        assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstEvent);
        assertTrue(form.isVisible());
        assertEquals(firstEvent.getEventType(), form.eventType.getValue());
    }

    private Event getFirstItem(Grid<Event> grid) {
        return( (ListDataProvider<Event>) grid.getDataProvider()).getItems().iterator().next();
    }
}