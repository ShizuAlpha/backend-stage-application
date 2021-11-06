package com.marsa.backendstageapplication.form;

import com.marsa.backendstageapplication.response.Response;
import com.marsa.backendstageapplication.user.User;
import com.marsa.backendstageapplication.user.UserService;
import com.marsa.backendstageapplication.userformreception.UserFormReception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class FormService {
    @Autowired
    FormRepository formRepository;
    @Autowired
    UserService userService;

    public Form getForm(Long id) {
        return this.formRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "form not found"));
    }

    public List<Form> getAllForms() {
        return this.formRepository.findAll();
    }

    public Form createForm(FormDTO form) {
        Form formToCreate = new Form();

        formToCreate.setName(form.getName());
        formToCreate.setDate(form.getDate());
        formToCreate.setFormString(form.getFormString());
        formToCreate.setOrientation(form.getOrientation());
        formToCreate.setUser(userService.getUser(form.getUser()));


        return this.formRepository.save(formToCreate);
    }

    public Form deleteForm(Long id) {
        Form formToDelete = this.getForm(id);
        this.formRepository.deleteById(id);
        System.out.println("Form deleted");
        return formToDelete;
    }

    public Form updateForm(Long formId, Form form) {
        Form formToUpdate = this.formRepository.findById(formId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"form not found"));

        formToUpdate.setName(form.getName());
        formToUpdate.setDate(form.getDate());
        formToUpdate.setFormString(form.getFormString());
        formToUpdate.setOrientation(form.getOrientation());

        return this.formRepository.save(formToUpdate);
    }

    public List<Response> getFormResponses(Long id) {
        Form form = this.formRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"form not found"));
        return form.getResponses();
    }

    public List<UserFormReception> getFormReceptions(Long id) {
        Form form = this.formRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"form not found"));
        return form.getReceptions();
    }

    public List<Form> getFormReceptionsByUser(Long id) {
        User user = this.userService.getUser(id);
        List<Form> filteredForms = new ArrayList<>();
        List<UserFormReception> sortedList = user.getReceptions();
        for (UserFormReception reception: sortedList) {
            filteredForms.add(reception.getForm());
        }
        return filteredForms;
    }

    public User getFormUser(Long id) {
        Form form = this.formRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"form not found"));
        return form.getUser();
    }
}