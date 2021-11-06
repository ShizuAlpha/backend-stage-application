package com.marsa.backendstageapplication.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marsa.backendstageapplication.form.Form;
import com.marsa.backendstageapplication.inputmodel.InputModel;
import com.marsa.backendstageapplication.response.Response;
import com.marsa.backendstageapplication.userformreception.UserFormReception;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "Users")
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "email", "username" }) })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Form> forms;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserFormReception> receptions;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Response> responses;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<InputModel> inputModels;

    private String userName;
    private String email;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private String terminal;

    @ManyToMany(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<Role> roles = new ArrayList<>();

    public User(String userName, String email, String password, String firstName, String lastName, String terminal, Collection<Role> userRole) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.terminal = terminal;
        this.roles = userRole;
    }

    public User(long id, List<Form> forms, List<UserFormReception> receptions, List<Response> responses, List<InputModel> inputModels, String userName, String email, String password, String firstName, String lastName, String terminal, Collection<Role> roles) {
        this.id = id;
        this.forms = forms;
        this.receptions = receptions;
        this.responses = responses;
        this.inputModels = inputModels;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.terminal = terminal;
        this.roles = roles;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String toString() {
        return "User [email=" + email + ", id=" + id + ", password=" + password
                + ", userName=" + userName + ", userRole=" + roles + ", firstName=" + firstName
                + ", lastName=" + lastName + ", terminal=" + terminal+ "]";
    }

}
