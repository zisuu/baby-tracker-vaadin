//package ch.finecloud.babytracker.views.list;
//
//import com.microsoft.playwright.Locator;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//class EventListViewIT extends PlaywrightIT {
//    @BeforeEach
//    void login() {
//        page.navigate("http://localhost:8080/login");
//
//        new LoginPO(page).login(System.getenv("user1@example.com"), System.getenv("password"));
//
//        Locator locator = page.locator("#view-title");
//        assertThat(locator.innerText()).isEqualTo("Organisationen");
//    }
//}
