package com.example.phonebook.view;

import com.example.phonebook.model.Contact;
import com.example.phonebook.service.ContactService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    private final ContactService contactService;
    private final Grid<Contact> grid = new Grid<>(Contact.class);
    private final Button addContactButton = new Button("Add Contact");

    public MainView(ContactService contactService) {
        this.contactService = contactService;

        configureGrid();
        add(grid, addContactButton);

        addContactButton.addClickListener(event -> getUI().ifPresent(ui -> ui.navigate(ContactFormView.class)));

        grid.addItemClickListener(event -> getUI().ifPresent(ui -> ui.navigate("form/" + event.getItem().getId())));

        updateList();
    }

    private void configureGrid() {
        grid.setColumns("name", "phoneNumber", "email");
        grid.addComponentColumn(this::createDeleteButton).setHeader("Actions");
    }

    private Button createDeleteButton(Contact contact) {
        Button deleteButton = new Button("Delete", event -> {
            contactService.deleteContactById(contact.getId());
            updateList();
        });
        return deleteButton;
    }

    private void updateList() {
        grid.setItems(contactService.getAllContacts());
    }
}