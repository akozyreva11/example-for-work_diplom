package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.mapper.UserMapper;
import ru.skypro.homework.model.UserModel;
import ru.skypro.homework.repository.UserModelRepository;
import ru.skypro.homework.service.UserService;

import java.io.*;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserModelRepository userModelRepository;
    private final UserMapper userMapper;


    public UserServiceImpl(UserModelRepository userModelRepository, UserMapper userMapper) {
        this.userModelRepository = userModelRepository;
        this.userMapper = userMapper;
    }

    public void createUserWithRegistrationInfo(UserDetails userDetails, RegisterDTO register) {
        UserModel creatingUser = new UserModel();
        creatingUser.setEmail(userDetails.getUsername());
        creatingUser.setPassword(userDetails.getPassword());
        creatingUser.setFirstName(register.getFirstName());
        creatingUser.setLastName(register.getLastName());
        creatingUser.setRole(register.getRole());
        creatingUser.setPhone(register.getPhone());
        userModelRepository.save(creatingUser);
    }

    @Override
    public UserModel findUserByUserName(String username) throws Exception {
        return userModelRepository.findOneByEmailIgnoreCase(username)
                .orElseThrow(() -> new Exception("User = " + username + " not found"));

    }

    @Override
    public UserModel updateUser(String username, UpdateUserDTO update) throws Exception {
        UserModel existingUser = findUserByUserName(username);
        userMapper.mapUpdateUserDTOToUserModel(update, existingUser);
        userModelRepository.save(existingUser);
        return existingUser;
    }

    @Override
    public void updateUserAvatar(String username, MultipartFile file) throws IOException {

    }

}