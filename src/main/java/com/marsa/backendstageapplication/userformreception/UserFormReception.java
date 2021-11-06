package com.marsa.backendstageapplication.userformreception;

import com.marsa.backendstageapplication.form.Form;
import com.marsa.backendstageapplication.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFormReception {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Form form;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    private Date dateReception;
    private int status;

    public UserFormReception(Form form, User user, Date dateReception, int status) {
        this.form = form;
        this.user = user;
        this.dateReception = dateReception;
        this.status = status;
    }
}
