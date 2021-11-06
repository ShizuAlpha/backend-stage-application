package com.marsa.backendstageapplication.userformreception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFormReceptionDTO {
    private Date dateReception;
    private int status;

    private  Long user;
    private  Long form;
}
