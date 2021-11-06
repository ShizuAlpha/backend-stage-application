package com.marsa.backendstageapplication.userformreception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/receptions")
public class UserFormReceptionController {
    @Autowired
    UserFormReceptionService receptionService;

    @GetMapping(path = "{receptionId}")
    public UserFormReception getReception(@PathVariable Long receptionId) {
        return this.receptionService.getReception(receptionId);
    }

    @GetMapping
    public List<UserFormReception> getAllReceptions() {
        return this.receptionService.getAllReceptions();
    }

//    @PostMapping
//    public UserFormReception createReception(@Valid @RequestBody UserFormReception reception) {
//        return this.receptionService.createReception(reception);
//    }

    @PostMapping
    public List<UserFormReception> createMultipleReceptions(@RequestBody List<UserFormReceptionDTO> receptions) {
        return this.receptionService.createMultipleReceptions(receptions);
    }

    @DeleteMapping("{receptionId}")
    public UserFormReception deleteReception(@PathVariable Long receptionId){
        return this.receptionService.deleteReception(receptionId);
    }

    @PutMapping(path = "{receptionId}")
    public UserFormReception updateReception(@PathVariable Long receptionId,@Valid @RequestBody UserFormReception reception){
        return  this.receptionService.updateReception(receptionId,reception);
    }

}
