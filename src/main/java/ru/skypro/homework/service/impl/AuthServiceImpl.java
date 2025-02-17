package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.error.ExceptionControllerAdvice;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;


@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserService userService;

    public AuthServiceImpl(UserDetailsManager manager,
                           PasswordEncoder passwordEncoder, UserService userService) {
        this.manager = manager;
        this.encoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            log.error("User " + userName + " is not registered in system");
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterDTO register) {
        if (manager.userExists(register.getUsername())) {
            log.error("User " + register.getUsername() + " is already registered in system");
            return false;
        }
        UserDetails userDetails = User.builder()
                .passwordEncoder(this.encoder::encode)
                .password(register.getPassword())
                .username(register.getUsername())
                .roles(register.getRole().name())
                .build();

        userService.createUserWithRegistrationInfo(userDetails, register);
        log.info("User " + register.getUsername() + " successfully registered in system");
        return true;
    }

}