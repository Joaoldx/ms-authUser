package com.ead.authUser.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.ead.authUser.dtos.UserDto;
import com.ead.authUser.models.UserModel;
import com.ead.authUser.services.UserService;
import com.ead.authUser.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(SpecificationTemplate.UserSpec spec,
                                            @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable, 
                                            @RequestParam(required = false) UUID courseId) {

        Page<UserModel> userModelPage = null;
        if (courseId != null) {
            userModelPage = userService.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable);
        } else {
            userModelPage = userService.findAll(spec, pageable);
        }

        if (!userModelPage.isEmpty()) {
            for (UserModel user : userModelPage.toList()) {
                user.add(linkTo(methodOn(UserController.class).getOneUser(user.getUserId())).withSelfRel());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable UUID userId) {
        Optional<UserModel> userModelOptional = userService.findById(userId);

        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
    }
    
    @DeleteMapping("{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable UUID userId) {
        log.debug("DELETE deleteUser userDto received {} ", userId);
        
        Optional<UserModel> userModelOptional = userService.findById(userId);
        
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        log.debug("DELETE deleteUser userId {} ", userId);
        log.info("DELETE deleted successfully {} ", userId);

        return ResponseEntity.status(HttpStatus.OK).body("User deleted succefully.");
    }
    
    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable UUID userId, @RequestBody @Validated(UserDto.UserView.UserPut.class)
                                                             @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userdto) {
        Optional<UserModel> userModelOptional = userService.findById(userId);

        log.debug("PUT updateUser userDto received {} ", userModelOptional);
        
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        
        var userModel = userModelOptional.get();
        
        userModel.setFullname(userdto.getFullname());
        userModel.setPhoneNumber(userdto.getPhoneNumber());
        userModel.setCpf(userdto.getCpf());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        
        userService.save(userModel);

        
        return ResponseEntity.status(HttpStatus.OK).body(userModel);
    }
    
    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable UUID userId, 
                                                @RequestBody @Validated(UserDto.UserView.PasswordPut.class)
                                                @JsonView(UserDto.UserView.PasswordPut.class) UserDto userdto) {
        
        log.debug("PUT updatePassword userDto received userId {} ", userdto.getUserId());

        Optional<UserModel> userModelOptional = userService.findById(userId);
        
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        
        if (!userModelOptional.get().getPassword().equals(userdto.getOldPassword())) {
            log.warn("Mismatch old password {} ", userdto.getUserId());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Mismatch old password!");
        }
        
        var userModel = userModelOptional.get();
        
        userModel.setPassword(userdto.getPassword());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        
        log.debug("PUT updateUser userPassword userId {} ", userModel.getUserId());
        log.info("Password updated successfully userId {} ", userModel.getUserId());

        userService.save(userModel);

        return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");
    }
    
    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable UUID userId,
                                                @RequestBody @Validated(UserDto.UserView.ImagePut.class) 
                                                @JsonView(UserDto.UserView.ImagePut.class) UserDto userdto) {

        log.debug("PUT updateImage userDto userId {} ", userdto.getUserId());

        Optional<UserModel> userModelOptional = userService.findById(userId);
        
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }        

        var userModel = userModelOptional.get();
        
        userModel.setImageUrl(userdto.getImageUrl());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        userService.save(userModel);

        log.debug("PUT updateImage userModel userId {} ", userModel.getUserId());
        log.info("Image updated successfully userId {} ", userModel.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");
    }

}
