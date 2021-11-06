package com.marsa.backendstageapplication.inputmodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/inputs")
public class InputModelController {
    @Autowired
    InputModelService inputModelService;

    @GetMapping(path = "{inputId}")
    public InputModel getInputModel(@PathVariable Long inputId) {
        return this.inputModelService.getInputModel(inputId);
    }

    @GetMapping
    public List<InputModel> getAllInputModels() {
        return this.inputModelService.getAllInputModels();
    }

    @PostMapping
    public InputModel createInputModel(@Valid @RequestBody InputModelDTO input) {
        return this.inputModelService.createInputModel(input);
    }

    @DeleteMapping("{inputId}")
    public InputModel deleteInputModel(@PathVariable Long inputId){
        return this.inputModelService.deleteInputModel(inputId);
    }

    @PutMapping(path = "{inputId}")
    public InputModel updateInputModel(@PathVariable Long inputId,@Valid @RequestBody InputModel input){
        return  this.inputModelService.updateInputModel(inputId,input);
    }
}
