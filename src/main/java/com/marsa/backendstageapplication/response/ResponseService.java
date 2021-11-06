package com.marsa.backendstageapplication.response;

import com.marsa.backendstageapplication.form.FormService;
import com.marsa.backendstageapplication.user.UserService;
import com.marsa.backendstageapplication.userformreception.UserFormReception;
import com.marsa.backendstageapplication.userformreception.UserFormReceptionDTO;
import com.marsa.backendstageapplication.userformreception.UserFormReceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ResponseService {
    @Autowired
    ResponseRepository responseRepository;
    @Autowired
    FormService formService;
    @Autowired
    UserService userService;
    @Autowired
    UserFormReceptionService receptionService;

    public Response getResponse(Long id) {
        return this.responseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Response not found"));
    }

    public Response getReceptionResponse(Long receptionId) {
        UserFormReception reception = this.receptionService.getReception(receptionId);
        Response response = new Response();
        for (Response element: userService.getUserResponses(reception.getUser().getId())
             ) {
            if (element.getForm().getId() == reception.getForm().getId()){
                response = element;
                return response;
            }
        }
        return response;
    }

    public List<Response> getAllResponses() {
        return this.responseRepository.findAll();
    }

    public Response createResponse(ResponseDTO response) {
        Response responseToCreate = new Response();
        responseToCreate.setStatus(response.getStatus());
        responseToCreate.setResponseString(response.getResponseString());
        responseToCreate.setDate(response.getDate());
        responseToCreate.setForm(formService.getForm(response.getForm()));
        responseToCreate.setUser(userService.getUser(response.getUser()));

        return this.responseRepository.save(responseToCreate);
    }

    public Response deleteResponse(Long id) {
        Response responseToDelete = this.getResponse(id);
        this.responseRepository.deleteById(id);
        System.out.println("Response deleted");
        return responseToDelete;
    }

    public Response updateResponse(Long responseId, Response response) {
        Response responseToUpdate = this.responseRepository.findById(responseId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Response not found"));

        responseToUpdate.setDate(response.getDate());
        responseToUpdate.setResponseString(response.getResponseString());
        responseToUpdate.setStatus(response.getStatus());

        return this.responseRepository.save(responseToUpdate);
    }
}
