package ch.finecloud.babytracker.views.list;

import ch.finecloud.babytracker.data.entity.Baby;
import ch.finecloud.babytracker.data.service.BabyTrackerService;
import ch.finecloud.babytracker.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope("prototype")
@PermitAll
@Route(value = "babies", layout = MainLayout.class)
@PageTitle("Babies | Baby Tracker")
public class BabyListView extends VerticalLayout {
    private final AuthenticationContext authenticationContext;
    Grid<Baby> grid = new Grid<>(Baby.class);
    TextField filterText = new TextField();
    BabyForm babyForm;
    BabyTrackerService service;

    public BabyListView(AuthenticationContext authenticationContext, BabyTrackerService service) {
        this.authenticationContext = authenticationContext;
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeBabyEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, babyForm);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, babyForm);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        babyForm = new BabyForm(service.findBabyByUserAccount_Email(null));
        babyForm.setWidth("25em");
        babyForm.addSaveListener(this::saveBaby); // <1>
        babyForm.addDeleteListener(this::deleteBaby); // <2>
        babyForm.addCloseListener(e -> closeBabyEditor()); // <3>
    }

    private void saveBaby(BabyForm.SaveBaby baby) {
        service.saveBaby(baby.getBaby());
        updateList();
        closeBabyEditor();
    }

    private void deleteBaby(BabyForm.DeleteBaby baby) {
        service.deleteBaby(baby.getBaby());
        updateList();
        closeBabyEditor();
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("name", "birthday");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(baby ->
                editBaby(baby.getValue()));
    }

    private Component getToolbar() {
        filterText.setPlaceholder("Filter by Baby...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addBabyButton = new Button("Add baby");
        addBabyButton.addClickListener(click -> addBaby());

        var toolbar = new HorizontalLayout(filterText, addBabyButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editBaby(Baby baby) {
        if (baby == null) {
            closeBabyEditor();
        } else {
            babyForm.setBaby(baby);
            babyForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeBabyEditor() {
        babyForm.setBaby(null);
        babyForm.setVisible(false);
        removeClassName("editing");
    }

    private void addBaby() {
        grid.asSingleSelect().clear();
        editBaby(new Baby());
    }


    private void updateList() {
        grid.setItems(service.findBabyByUserAccount_Email(filterText.getValue()));
    }


    private String getEmail() {
        return authenticationContext.getPrincipalName().isPresent() ? authenticationContext.getPrincipalName().get() : "";
    }
}