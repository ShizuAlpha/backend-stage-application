package com.marsa.backendstageapplication.user;

import com.marsa.backendstageapplication.form.Form;
import com.marsa.backendstageapplication.inputmodel.InputModel;
import com.marsa.backendstageapplication.response.Response;
import com.marsa.backendstageapplication.userformreception.UserFormReception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.*;

@Service @RequiredArgsConstructor @Transactional @Slf4j
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public User getUser(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(UserSupDTO user) {
        User newUser = new User();
        newUser.setUserName(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setTerminal(user.getTerminal());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = getRoleByName("FORM_READER");
        newUser.getRoles().add(role);
        return this.userRepository.save(newUser);
    }

    public User deleteUser(Long id) {
        User userToDelete = this.getUser(id);
        this.userRepository.deleteById(id);
        System.out.println("User deleted");
        return userToDelete;
    }

    public User updateUser(Long userId, UserModDTO user) {
        User userToUpdate = this.userRepository.findById(userId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));

        userToUpdate.setUserName(user.getUsername());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setTerminal(user.getTerminal());
        userToUpdate.getRoles().clear();
        user.getRoles().forEach(role ->{
            userToUpdate.getRoles().add(getRoleByName(role));
        });

        return this.userRepository.save(userToUpdate);
    }

    public List<Form> getUserForms(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        return user.getForms();
    }

    public List<UserFormReception> getUserReceptions(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        List<UserFormReception> sortedList = user.getReceptions();
        return sortedList;
    }

    public List<Response> getUserResponses(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        return user.getResponses();
    }

    public List<InputModel> getUserInputs(Long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));
        return user.getInputModels();
    }

    public User getUserByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
    }

    public Role saveRole(Role role){
        return roleRepository.save(role);
    }

    public void addRoleToUser(String username, String roleName) {
        User user = getUserByUserName(username);
        Role role = getRoleByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUserName(username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setId(1);
        adminRole.setName("ADMIN");
        roleRepository.save(adminRole);

        Role formCreatorRole = new Role();
        formCreatorRole.setId(2);
        formCreatorRole.setName("FORM_CREATOR");
        roleRepository.save(formCreatorRole);

        Role formReaderRole = new Role();
        formReaderRole.setId(3);
        formReaderRole.setName("FORM_READER");
        roleRepository.save(formReaderRole);

        User adminUser = new User();
        adminUser.setId(1L);
        adminUser.setUserName("admin");
        adminUser.setPassword(passwordEncoder.encode("admin"));
        adminUser.setFirstName("Heddi");
        adminUser.setLastName("Omar");
        adminUser.setEmail("heddiomar@gmail.com");
        adminUser.setTerminal("adminTerminal");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRoles(adminRoles);
//        userRepository.save(new User((long)1, "admin", "heddiomar@gmail.com", passwordEncoder.encode("admin"), "Heddi", "Omar", "T1", new ArrayList<>()));

		addRoleToUser("admin", "ADMIN");
    }
}
