package ch.finecloud.babytracker.views;

import ch.finecloud.babytracker.data.service.BabyTrackerService;
import ch.finecloud.babytracker.views.list.RegistrationForm;
import ch.finecloud.babytracker.views.list.RegistrationFormBinder;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.crypto.password.PasswordEncoder;

@Route("registeruseraccount")
@PageTitle("Register User Account | Baby Tracker")
@Uses(Icon.class)
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {
    private final BabyTrackerService babyTrackerService;

    private final PasswordEncoder passwordEncoder;

    public RegistrationView(BabyTrackerService babyTrackerService, PasswordEncoder passwordEncoder) {
        this.babyTrackerService = babyTrackerService;
        this.passwordEncoder = passwordEncoder;
        RegistrationForm registrationForm = new RegistrationForm();
        // Center the RegistrationForm
        setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);
        add(registrationForm);

        RegistrationFormBinder registrationFormBinder = new RegistrationFormBinder(registrationForm, this.babyTrackerService, this.passwordEncoder);
        registrationFormBinder.addBindingAndValidation();
    }
}
