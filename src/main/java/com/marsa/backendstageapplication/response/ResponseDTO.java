package com.marsa.backendstageapplication.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private Date date;
    private String responseString;
    private int status;


    private Long form;
    private Long user;
    private Long id;

    public ResponseDTO(Date date, String responseString, int status, Long form, Long user) {
        this.date = date;
        this.responseString = responseString;
        this.status = status;
        this.form = form;
        this.user = user;
    }
}
