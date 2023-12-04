package ch.finecloud.babytracker.views.list;

import ch.finecloud.babytracker.TestBabyTrackerApplication;
import ch.finecloud.babytracker.bootstrap.BootstrapData;
import ch.finecloud.babytracker.service.csv.BabyCsvService;
import ch.finecloud.babytracker.service.csv.EventCsvService;
import ch.finecloud.babytracker.service.csv.UserCsvService;
import com.github.mvysny.kaributesting.mockhttp.MockRequest;
import com.github.mvysny.kaributesting.v10.MockVaadin;
import com.github.mvysny.kaributesting.v10.Routes;
import com.github.mvysny.kaributesting.v10.spring.MockSpringServlet;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.spring.SpringServlet;
import kotlin.jvm.functions.Function0;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.github.mvysny.kaributesting.v10.LocatorJ._get;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({TestBabyTrackerApplication.class, BootstrapData.class, BabyCsvService.class, UserCsvService.class, EventCsvService.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class KaribuTest {

    private static Routes routes;

    @Autowired
    protected ApplicationContext ctx;

    @BeforeAll
    public static void discoverRoutes() {
        Locale.setDefault(Locale.ENGLISH);
        routes = new Routes().autoDiscoverViews("ch.finecloud.babytracker.views");
    }

    @BeforeEach
    public void setup() {
        final Function0<UI> uiFactory = UI::new;
        final SpringServlet servlet = new MockSpringServlet(routes, ctx, uiFactory);
        MockVaadin.setup(uiFactory, servlet);
    }

    @AfterEach
    public void tearDown() {
        logout();
        MockVaadin.tearDown();
    }

    protected void login(String user, String pass, final List<String> roles) {
        // taken from https://www.baeldung.com/manually-set-user-authentication-spring-security
        // also see https://github.com/mvysny/karibu-testing/issues/47 for more details.
        final List<SimpleGrantedAuthority> authorities =
                roles.stream().map(it -> new SimpleGrantedAuthority(it)).collect(Collectors.toList());

        UserDetails userDetails = new User(user, pass, authorities);
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(userDetails, pass, authorities);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(authReq);

        // however, you also need to make sure that ViewAccessChecker works properly;
        // that requires a correct MockRequest.userPrincipal and MockRequest.isUserInRole()
        final MockRequest request = (MockRequest) VaadinServletRequest.getCurrent().getRequest();
        request.setUserPrincipalInt(authReq);
        request.setUserInRole((principal, role) -> roles.contains(role));
    }

    protected void logout() {
        try {
            SecurityContextHolder.getContext().setAuthentication(null);
            if (VaadinServletRequest.getCurrent() != null) {
                final MockRequest request = (MockRequest) VaadinServletRequest.getCurrent().getRequest();
                request.setUserPrincipalInt(null);
                request.setUserInRole((principal, role) -> false);
            }
        } catch (IllegalStateException e) {
            // Ignored
        }
    }

//    protected Grid<Event> navigateToSeriesList() {
//        UI.getCurrent().navigate(EventListView.class);
//
//        H1 h1 = _get(H1.class, spec -> spec.withId("view-title"));
//        assertThat(h1.getText()).isEqualTo("Organizations");
//
//        Grid<Event> eventGrid = _get(Grid.class, spec -> spec.withId("event-grid"));
//        assertThat(GridKt._size(eventGrid)).isEqualTo(2);
//
//        GridKt._getCellComponent(eventGrid, 0, "edit-column").getChildren()
//                .filter(component -> component instanceof Button).findFirst().map(component -> (Button) component)
//                .ifPresent(Button::click);
//
//        h1 = _get(H1.class, spec -> spec.withId("view-title"));
//        assertThat(h1.getText()).isEqualTo("Series");
//
//        Grid<Event> seriesGrid = _get(Grid.class, spec -> spec.withId("series-grid"));
//        assertThat(GridKt._size(seriesGrid)).isEqualTo(2);
//
//        Event event = GridKt._get(seriesGrid, 0);
//        assertThat(event.getEventType()).isEqualTo(EventType.BATHING);
//
//        return seriesGrid;
//    }
}
