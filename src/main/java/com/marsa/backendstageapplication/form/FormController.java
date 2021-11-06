package com.marsa.backendstageapplication.form;

import com.marsa.backendstageapplication.response.Response;
import com.marsa.backendstageapplication.user.User;
import com.marsa.backendstageapplication.userformreception.UserFormReception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/forms")
public class FormController {
    @Autowired
    FormService formService;

    @GetMapping(path = "{formId}")
    public Form getForm(@PathVariable Long formId) {
        return this.formService.getForm(formId);
    }

    @GetMapping
    public List<Form> getAllForms() {
        return this.formService.getAllForms();
    }

    @PostMapping
    public Form createForm(@Valid @RequestBody FormDTO form) {
        return this.formService.createForm(form);
    }


    @DeleteMapping("{formId}")
    public Form deleteForm(@PathVariable Long formId){
        return this.formService.deleteForm(formId);
    }

    @PutMapping(path = "{formId}")
    public Form updateForm(@PathVariable Long formId,@Valid @RequestBody Form form){
        return  this.formService.updateForm(formId,form);
    }

    @GetMapping(path = "{formId}/responses")
    public List<Response> getFormResponses(@PathVariable Long formId) {
        return this.formService.getFormResponses(formId);
    }

    @GetMapping(path = "{formId}/receptions")
    public List<UserFormReception> getFormReceptions(@PathVariable Long formId) {
        return this.formService.getFormReceptions(formId);
    }

    @GetMapping(path = "{userId}/filteredReceptions")
    public List<Form> getFormReceptionsByUser(@PathVariable Long userId) {
        return this.formService.getFormReceptionsByUser(userId);
    }

    @GetMapping("{formId}/excel")
    public void exportToExcel(HttpServletResponse response, @PathVariable Long formId) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        Form form = this.formService.getForm(formId);
        FormResponseExcelExporter excelExporter = new FormResponseExcelExporter(form);

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + form.getName() + "_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        excelExporter.export(response);
    }

    @GetMapping(path = "{formId}/user")
    public User getFormUser(@PathVariable Long formId) {
        return this.formService.getFormUser(formId);
    }
}
