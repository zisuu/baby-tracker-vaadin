//package ch.finecloud.babytracker.views.list.it;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.fail;
//
//class EventListViewIT extends PlaywrightIT {
//    @BeforeEach
//    void login() {
//        page.navigate("http://localhost:8080/");
//
//        new LoginPO(page).login("userAccount1@example.com", "password1");
//
////        Locator locator = page.locator("view-title");
////        assertThat(locator.innerText()).isEqualTo("Events");
//    }
//
//
//    @Test
//    public void testEvent() {
//        page.waitForSelector("button:has-text('Add event')").click();
//        page.click("label:has-text('Baby')");
//        page.click("option:has-text('Max')");
//        page.click("label:has-text('EventType')");
//        page.click("option:has-text('Sleeping')");
//        page.click("button:has-text('Save')");
//        page.click("vaadin-grid-cell-content:nth-child(37)");
//        page.click("button:has-text('Delete')");
//        page.click("button:has-text('Log out userAccount1@example.com')");
//
//    }
//
//}
