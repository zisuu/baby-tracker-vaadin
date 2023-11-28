package ch.finecloud.babytracker.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.Lumo;

@Route("login")
@PageTitle("Login | Baby Tracker")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	private final LoginForm login = new LoginForm();
	private H1 viewTitle;

	public LoginView(){
		Button register = new Button("Register new User Account", event -> UI.getCurrent().navigate("registeruseraccount"));
		register.setId("registerButton");
		Button toggleButton = new Button("Dark mode", click -> {
			ThemeList themeList = UI.getCurrent().getElement().getThemeList();

			if (themeList.contains(Lumo.DARK)) {
				themeList.remove(Lumo.DARK);
			} else {
				themeList.add(Lumo.DARK);
			}
		});
		addClassName("login-view");
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		login.setForgotPasswordButtonVisible(false);
		login.setAction("login");
		viewTitle = new H1();
		viewTitle.setId("view-title");
		viewTitle.setText("Baby Tracker");
		add(viewTitle);
		add(new H6("version 0.0.15"), login, register, toggleButton);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		// inform the user about an authentication error
		if(beforeEnterEvent.getLocation()
        .getQueryParameters()
        .getParameters()
        .containsKey("error")) {
            login.setError(true);
        }
	}
}