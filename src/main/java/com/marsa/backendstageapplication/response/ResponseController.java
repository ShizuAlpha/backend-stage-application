package com.marsa.backendstageapplication.response;

import com.marsa.backendstageapplication.userformreception.UserFormReceptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/responses")
public class ResponseController {
    @Autowired
    ResponseService responseService;

    @GetMapping(path = "{responseId}")
    public Response getResponse(@PathVariable Long responseId) {
        return this.responseService.getResponse(responseId);
    }

    @GetMapping(path = "{responseId}/receptionResponce")
    public Response getReceptionResponse(@PathVariable Long responseId) {
        return this.responseService.getReceptionResponse(responseId);
    }

    @GetMapping
    public List<Response> getAllResponses() {
        return this.responseService.getAllResponses();
    }

    @PostMapping
    public Response createResponse(@Valid @RequestBody ResponseDTO response) {
        return this.responseService.createResponse(response);
    }

    @DeleteMapping("{responseId}")
    public Response deleteResponse(@PathVariable Long responseId){
        return this.responseService.deleteResponse(responseId);
    }

    @PutMapping(path = "{responseId}")
    public Response updateResponse(@PathVariable Long responseId,@Valid @RequestBody Response response){
        return  this.responseService.updateResponse(responseId,response);
    }

}
