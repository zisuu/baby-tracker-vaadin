package ch.finecloud.babytracker.views.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ch.finecloud.babytracker.data.entity.Event;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ListViewTest {

    static {
        // Prevent Vaadin Development mode to launch browser window
        System.setProperty("vaadin.launch-browser", "false");
    }

    @Autowired
    private ListView listView;

    @Test
    public void formShownWhenContactSelected() {
        Grid<Event> grid = listView.grid;
        Event firstEvent = getFirstItem(grid);

        EventForm form = listView.form;

        assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstEvent);
        assertTrue(form.isVisible());
        assertEquals(firstEvent.getEventType(), form.eventType.getValue());
    }

    private Event getFirstItem(Grid<Event> grid) {
        return( (ListDataProvider<Event>) grid.getDataProvider()).getItems().iterator().next();
    }
}