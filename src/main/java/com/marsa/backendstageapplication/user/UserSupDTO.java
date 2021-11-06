package com.marsa.backendstageapplication.user;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserSupDTO {

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String terminal;
}
