package com.example.phonebook.service;

import com.example.phonebook.model.Contact;
import com.example.phonebook.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public Contact getContactById(String id) {
        return contactRepository.findById(id).orElse(null);
    }

    public void deleteContactById(String id) {
        contactRepository.deleteById(id);
    }
}
