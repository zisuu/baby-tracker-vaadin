package ch.finecloud.babytracker.views.list;


import ch.finecloud.babytracker.data.entity.Role;
import ch.finecloud.babytracker.views.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.login.LoginForm;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.mvysny.kaributesting.v10.LocatorJ._get;
import static com.github.mvysny.kaributesting.v10.LoginFormKt._login;
import static org.assertj.core.api.Assertions.assertThat;

class LoginViewTest extends KaribuTest {

    @Test
    void login_with_unknown_user() {
        UI.getCurrent().navigate(LoginView.class);

        try {
            _login(_get(LoginForm.class), "not.existing@user.com", "pass");
        } catch (IllegalStateException e) {
            // From GoogleAnalyticsTracker. Ignore
        }

        assertThat(_get(LoginForm.class).getElement().getOuterHTML()).isEqualTo("<vaadin-login-form></vaadin-login-form>");
    }

    @Test
    void already_logged_in() {
        login("userAccount1@example.com", "password1", List.of(Role.USER.name()));

        UI.getCurrent().navigate(EventListView.class);

        Grid eventGrid = _get(Grid.class, spec -> spec.withId("eventGrid"));
        assertThat(eventGrid.getClassName()).isEqualTo("contact-grid");
    }

}

