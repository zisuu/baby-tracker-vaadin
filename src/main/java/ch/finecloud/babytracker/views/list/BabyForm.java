package ch.finecloud.babytracker.views.list;

import ch.finecloud.babytracker.data.entity.Baby;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class BabyForm extends FormLayout {
    TextField name = new TextField("Name");
    DatePicker birthday = new DatePicker();
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    // Other fields omitted
    Binder<Baby> binder = new BeanValidationBinder<>(Baby.class);

    public BabyForm(List<Baby> babies) {
        addClassName("baby-form");
        binder.bindInstanceFields(this);
        add(name,
                birthday,
                createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(baby -> validateAndSave()); // <1>
        delete.addClickListener(baby -> fireEvent(new DeleteBaby(this, binder.getBean()))); // <2>
        close.addClickListener(baby -> fireEvent(new CloseBaby(this))); // <3>

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid())); // <4>
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveBaby(this, binder.getBean())); // <6>
        }
    }


    public void setBaby(Baby baby) {
        binder.setBean(baby); // <1>
    }

    // Babyss
    public static abstract class BabyFormEvent extends ComponentEvent<BabyForm> {
        private Baby baby;

        protected BabyFormEvent(BabyForm source, Baby baby) {
            super(source, false);
            this.baby = baby;
        }

        public Baby getBaby() {
            return baby;
        }
    }

    public static class SaveBaby extends BabyFormEvent {
        SaveBaby(BabyForm source, Baby baby) {
            super(source, baby);
        }
    }

    public static class DeleteBaby extends BabyFormEvent {
        DeleteBaby(BabyForm source, Baby baby) {
            super(source, baby);
        }

    }

    public static class CloseBaby extends BabyFormEvent {
        CloseBaby(BabyForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteBaby> listener) {
        return addListener(DeleteBaby.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveBaby> listener) {
        return addListener(SaveBaby.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<CloseBaby> listener) {
        return addListener(CloseBaby.class, listener);
    }


}

