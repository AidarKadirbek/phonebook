package com.example.phonebook.view;

import com.example.phonebook.model.Contact;
import com.example.phonebook.service.ContactService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route("form/:contactId?")
public class ContactFormView extends VerticalLayout implements BeforeEnterObserver {

    private final ContactService contactService;
    private TextField nameField = new TextField("Name");
    private TextField phoneField = new TextField("Phone Number");
    private TextField emailField = new TextField("Email");
    private Button saveButton = new Button("Save");
    private Button cancelButton = new Button("Cancel");
    private String contactId;

    @Autowired
    public ContactFormView(ContactService contactService) {
        this.contactService = contactService;

        saveButton.addClickListener(e -> saveContact());
        cancelButton.addClickListener(e -> navigateToMainView());

        add(nameField, phoneField, emailField, saveButton, cancelButton);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        contactId = event.getRouteParameters().get("contactId").orElse(null);
        if (contactId != null) {
            Contact contact = contactService.getContactById(contactId);
            if (contact != null) {
                nameField.setValue(contact.getName());
                phoneField.setValue(contact.getPhoneNumber());
                emailField.setValue(contact.getEmail());
            } else {
                showNotification("Contact not found!");
                navigateToMainView();
            }
        }
    }

    private void saveContact() {
        Contact contact = new Contact(contactId, nameField.getValue(), phoneField.getValue(), emailField.getValue());
        contactService.saveContact(contact);
        navigateToMainView();
    }

    private void navigateToMainView() {
        getUI().ifPresent(ui -> ui.navigate(MainView.class));
    }

    private void showNotification(String message) {
        Notification.show(message, 3000, Notification.Position.MIDDLE);
    }
}