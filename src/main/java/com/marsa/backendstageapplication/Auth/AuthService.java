package com.marsa.backendstageapplication.Auth;

import com.marsa.backendstageapplication.user.User;
import com.marsa.backendstageapplication.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;

    public User registerNewUser(User user) {
        return userRepository.save(user);
    }


}
