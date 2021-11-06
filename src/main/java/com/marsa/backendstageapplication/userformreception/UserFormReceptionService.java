package com.marsa.backendstageapplication.userformreception;

import com.marsa.backendstageapplication.form.FormService;
import com.marsa.backendstageapplication.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserFormReceptionService {
    @Autowired
    UserFormReceptionRepository receptionRepository;
    @Autowired
    UserService userService;
    @Autowired
    FormService formService;

    public UserFormReception getReception(Long id) {
        return this.receptionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reception not found"));
    }

    public List<UserFormReception> getAllReceptions() {
        return this.receptionRepository.findAll();
    }

    public UserFormReception createReception(UserFormReception reception) {
        return this.receptionRepository.save(reception);
    }

    public List<UserFormReception> createMultipleReceptions(List<UserFormReceptionDTO> receptionsDTO) {
        List<UserFormReception> receptions = new ArrayList<>();
        if (receptionsDTO.isEmpty()){
            return receptions;
        }
        for (UserFormReceptionDTO reception: receptionsDTO) {
            receptions.add(new UserFormReception(
                    this.formService.getForm(reception.getForm()),
                    this.userService.getUser(reception.getUser()),
                    reception.getDateReception(),
                    reception.getStatus()
            ));
        }
        return this.receptionRepository.saveAll(receptions);
    }

    public UserFormReception deleteReception(Long id) {
        UserFormReception receptionToDelete = this.getReception(id);
        this.receptionRepository.deleteById(id);
        System.out.println("Reception deleted");
        return receptionToDelete;
    }

    public UserFormReception updateReception(Long receptionId, UserFormReception reception) {
        UserFormReception receptionToUpdate = this.receptionRepository.findById(receptionId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Reception not found"));

        receptionToUpdate.setDateReception(reception.getDateReception());
        receptionToUpdate.setStatus(reception.getStatus());

        return this.receptionRepository.save(receptionToUpdate);
    }

}
