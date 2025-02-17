package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import javax.validation.Validator;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.dto.mapper.UserMapper;
import ru.skypro.homework.model.UserModel;
import ru.skypro.homework.service.UserService;

import java.io.IOException;


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/users")
public class UserController {

    private final UserDetailsManager manager;
    private final UserService userService;
    private final UserMapper mapper;
    private final ValidationUtils validationUtils;

    public UserController(UserDetailsManager manager, UserService userService, UserMapper mapper, ValidationUtils validationUtils) {
        this.manager = manager;
        this.userService = userService;
        this.mapper = mapper;
        this.validationUtils = validationUtils;
    }

    @Operation(
            summary = "Обновление пароля",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    )
            }, tags = "Пользователи"
    )
    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPasswordDTO newPassword) {
        validationUtils.validateRequest(newPassword);
        manager.changePassword(newPassword.getCurrentPassword(), newPassword.getNewPassword());
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получение информации об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UserDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            }, tags = "Пользователи"
    )
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUser(Authentication authentication) {
        UserModel user = userService.findUserByUserName(authentication.getName());
        UserDTO userDTO = mapper.mapUserModelToUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "Обновление информации об авторизованном пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = UpdateUserDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            }, tags = "Пользователи"
    )
    @PatchMapping("/me")
    public ResponseEntity<UpdateUserDTO> updateUser(@RequestBody UpdateUserDTO update, Authentication authentication) {
        validationUtils.validateRequest(update);
        UserModel updatedUser = userService.updateUser(authentication.getName(), update);
        UpdateUserDTO updatedUserDTO = mapper.mapUserModelToUpdateUserDTO(updatedUser);
        return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "Обновление аватара авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            }, tags = "Пользователи"
    )
    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/me/image")
    public ResponseEntity<?> updateUserImage(@RequestPart("image") MultipartFile image, @NotNull Authentication authentication) throws IOException {
        validationUtils.validateImageFile(image);
        userService.updateUserAvatar(authentication.getName(), image);
        return ResponseEntity.ok().build();
    }
}