package ch.finecloud.babytracker.views.list;

import ch.finecloud.babytracker.data.entity.Baby;
import ch.finecloud.babytracker.data.entity.Event;
import ch.finecloud.babytracker.data.entity.EventType;
import ch.finecloud.babytracker.data.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BabyFormTest extends KaribuTest {
    private List<Baby> babies;
    private Baby testBaby;
    private Baby baby1;
    private Baby baby2;

    @BeforeEach
    public void setupData() {
        babies = new ArrayList<>();
        testBaby = new Baby();
        baby1 = new Baby();
        baby1.setName("Baby Name1");
        baby2 = new Baby();
        baby2.setName("Baby Name2");
        babies.add(testBaby);
        babies.add(baby1);
        babies.add(baby2);

        testBaby.setName("Test Baby");
        testBaby.setBirthday(LocalDate.of(2021, 1, 1));
    }

    @Test
    public void formFieldsPopulated() {
        BabyForm form = new BabyForm(babies);
        form.setBaby(testBaby);
        assertEquals("Test Baby", form.name.getValue());
        assertEquals(LocalDate.of(2021, 1, 1), form.birthday.getValue());
    }

    @Test
    public void saveEventHasCorrectValues() {
        BabyForm form = new BabyForm(babies);
        Baby baby = new Baby();
        form.setBaby(baby);
        form.name.setValue("Test Baby2");
        form.birthday.setValue(LocalDate.of(2023, 1, 1));

        AtomicReference<Baby> savedBabyRef = new AtomicReference<>(null);
        form.addSaveListener(e -> {
            savedBabyRef.set(e.getBaby());
        });
        form.save.click();
        Baby savedBaby = savedBabyRef.get();

        assertEquals("Test Baby2", savedBaby.getName());
        assertEquals(LocalDate.of(2023, 1, 1), savedBaby.getBirthday());
    }
}