//package ch.finecloud.babytracker.views.list.it;
//
//import com.microsoft.playwright.Locator;
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class LoginViewIT extends PlaywrightIT {
//    @Test
//    void loginForm() {
//        page.navigate("http://localhost:8080/login");
//        Locator viewTitle = page.locator("#view-title");
//        assertThat(viewTitle.innerText()).isEqualTo("Baby Tracker");
//        Locator usernameField = page.locator("vaadin-text-field[name='username'] > input");
//        assertThat(usernameField.innerText()).isEqualTo("");
//        Locator passwordField = page.locator("vaadin-password-field[name='password'] > input");
//        assertThat(passwordField.innerText()).isEqualTo("");
//        Locator registerButton = page.locator("#registerButton");
//        assertThat(registerButton.innerText()).isEqualTo("Register new User Account");
//
//    }
//}
