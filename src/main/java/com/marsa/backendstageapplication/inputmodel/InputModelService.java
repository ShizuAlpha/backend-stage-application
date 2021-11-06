package com.marsa.backendstageapplication.inputmodel;

import com.marsa.backendstageapplication.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class InputModelService {
    @Autowired
    InputModelRepository inputModelRepository;
    @Autowired
    UserService userService;

    public InputModel getInputModel(Long id) {
        return this.inputModelRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "InputModel not found"));
    }

    public List<InputModel> getAllInputModels() {
        return this.inputModelRepository.findAll();
    }

    public InputModel createInputModel(InputModelDTO inputModel) {
        InputModel inputModelToCreate = new InputModel();

        inputModelToCreate.setNature(inputModel.getNature());
        inputModelToCreate.setLabel(inputModel.getLabel());
        inputModelToCreate.setRequired(inputModel.isRequired());
        inputModelToCreate.setMultiple(inputModel.isMultiple());
        inputModelToCreate.setSelectEntries(inputModel.getSelectEntries());
        inputModelToCreate.setType(inputModel.getType());
        inputModelToCreate.setFontSize(inputModel.getFontSize());
        inputModelToCreate.setFontWeight(inputModel.getFontWeight());
        inputModelToCreate.setTextAlign(inputModel.getTextAlign());
        inputModelToCreate.setUser(userService.getUser(inputModel.getUser()));

        return this.inputModelRepository.save(inputModelToCreate);
    }

    public InputModel deleteInputModel(Long id) {
        InputModel inputModelToDelete = this.getInputModel(id);
        this.inputModelRepository.deleteById(id);
        System.out.println("InputModel deleted");
        return inputModelToDelete;
    }

    public InputModel updateInputModel(Long inputId, InputModel inputModel) {
        InputModel inputModelToUpdate = this.inputModelRepository.findById(inputId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"InputModel not found"));

        inputModelToUpdate.setNature(inputModel.getNature());
        inputModelToUpdate.setLabel(inputModel.getLabel());
        inputModelToUpdate.setRequired(inputModel.isRequired());
        inputModelToUpdate.setMultiple(inputModel.isMultiple());
        inputModelToUpdate.setSelectEntries(inputModel.getSelectEntries());
        inputModelToUpdate.setType(inputModel.getType());
        inputModelToUpdate.setFontSize(inputModel.getFontSize());
        inputModelToUpdate.setFontWeight(inputModel.getFontWeight());
        inputModelToUpdate.setTextAlign(inputModel.getTextAlign());

        return this.inputModelRepository.save(inputModelToUpdate);
    }

}
