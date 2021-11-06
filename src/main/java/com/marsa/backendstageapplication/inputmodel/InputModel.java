package com.marsa.backendstageapplication.inputmodel;

import com.marsa.backendstageapplication.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    private int nature;
    private String label;
    private boolean isRequired;
    private String type;
    private String selectEntries;
    private boolean isMultiple;
    private String fontSize;
    private String fontWeight;
    private String textAlign;
}

