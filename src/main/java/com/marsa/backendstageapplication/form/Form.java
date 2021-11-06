package com.marsa.backendstageapplication.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marsa.backendstageapplication.response.Response;
import com.marsa.backendstageapplication.user.User;
import com.marsa.backendstageapplication.userformreception.UserFormReception;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.w3c.dom.Text;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "form", cascade=CascadeType.REMOVE)
    private List<UserFormReception> receptions;

    @JsonIgnore
    @OneToMany(mappedBy = "form")
    private List<Response> responses;

    private String name;
    private Date date;
    @Column(columnDefinition="TEXT")
    private String formString;
    private Boolean orientation;

}
