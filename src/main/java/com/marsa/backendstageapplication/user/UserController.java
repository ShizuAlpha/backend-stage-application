package com.marsa.backendstageapplication.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marsa.backendstageapplication.form.Form;
import com.marsa.backendstageapplication.inputmodel.InputModel;
import com.marsa.backendstageapplication.response.Response;
import com.marsa.backendstageapplication.userformreception.UserFormReception;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/users")
public class UserController {
    @Autowired
    UserService userService;

//    @PostConstruct
//    public void initRoleAndUser() {
//        userService.initRoleAndUser();
//    }


    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasAnyRole('ADMIN','FORM_READER')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('FORM_READER')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }

    @GetMapping(path = "{userId}")
    public User getUser(@PathVariable Long userId) {
        return this.userService.getUser(userId);
    }

    @GetMapping
    public List<User> getAllUsers() { return this.userService.getAllUsers(); }

    @PostMapping(path = "/registerNewUser")
    public User createUser(@Valid @RequestBody UserSupDTO user) {
        return this.userService.createUser(user);
    }

    @DeleteMapping("{userId}")
    public User deleteUser(@PathVariable Long userId){
        return this.userService.deleteUser(userId);
    }

    @PutMapping(path = "{userId}")
    public User updateUser(@PathVariable Long userId,@Valid @RequestBody UserModDTO user){
        return  this.userService.updateUser(userId,user);
    }

    @GetMapping(path = "{userId}/forms")
    public List<Form> getUserForms(@PathVariable Long userId) {
        return this.userService.getUserForms(userId);
    }

    @GetMapping(path = "{userId}/responses")
    public List<Response> getUserResponses(@PathVariable Long userId) {
        return this.userService.getUserResponses(userId);
    }

    @GetMapping(path = "{userId}/receptions")
    public List<UserFormReception> getUserReceptions(@PathVariable Long userId) {
        return this.userService.getUserReceptions(userId);
    }

    @GetMapping(path = "{userId}/inputs")
    public List<InputModel> getUserInputs(@PathVariable Long userId) {
        return this.userService.getUserInputs(userId);
    }


    @GetMapping(path = "{username}/username")
    public User getUserByUserName(@PathVariable String username) {
        return this.userService.getUserByUserName(username);
    }

    @GetMapping(path = "{roleName}/username")
    public Role getRoleByName(@PathVariable String roleName) {
        return this.userService.getRoleByName(roleName);
    }

    @PostMapping("/role/addtouser")
    public void addRoleToUser(@RequestBody RoleToUser rtu) {
        userService.addRoleToUser(rtu.getUsername(), rtu.getRolename());
    }

    @GetMapping("/refreshtoken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUserByUserName(username);

                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 12*60*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

}

@Data
class RoleToUser {
    private String username;
    private String rolename;
}
