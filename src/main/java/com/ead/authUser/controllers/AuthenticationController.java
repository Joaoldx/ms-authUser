package com.ead.authUser.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.authUser.dtos.UserDto;
import com.ead.authUser.enums.UserStatus;
import com.ead.authUser.enums.UserType;
import com.ead.authUser.models.UserModel;
import com.ead.authUser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/singup")
    public ResponseEntity<Object> registerUser(@RequestBody
                                @JsonView(UserDto.UserView.RegistrationPost.class) @Validated(UserDto.UserView.RegistrationPost.class)  UserDto userDto) {

        log.debug("POST registerUser userDto received {}", userDto.toString());

        if (userService.existsByUsername(userDto.getUsername())) {
            log.warn("Username {} is already taken", userDto.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is already taken!");
        }
        
        if (userService.existsByUsername(userDto.getEmail())) {
            log.warn("Email {} is already taken", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is already taken!");
        }

        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);

        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.STUDENT);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        userService.save(userModel);
        
        log.debug("POST registerUser userDto saved {}", userDto.toString());
        log.info("User saved successfully userId {}", userDto.getUserId());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
    }
}
