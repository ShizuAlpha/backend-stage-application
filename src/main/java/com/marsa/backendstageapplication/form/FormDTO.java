package com.marsa.backendstageapplication.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormDTO {

    private String name;
    private Date date;
    private String formString;
    private Boolean orientation;

    private Long user;
}
