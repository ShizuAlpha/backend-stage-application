package com.marsa.backendstageapplication.inputmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputModelDTO {

    private int nature;
    private String label;
    private boolean isRequired;
    private String type;
    private String selectEntries;
    private boolean isMultiple;
    private String fontSize;
    private String fontWeight;
    private String textAlign;

    private Long user;
}
