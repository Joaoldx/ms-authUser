package com.ead.authUser.controllers;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ead.authUser.clients.CourseClient;
import com.ead.authUser.dtos.CourseDto;
import com.ead.authUser.dtos.UserCourseDto;
import com.ead.authUser.models.UserCourseModel;
import com.ead.authUser.models.UserModel;
import com.ead.authUser.services.UserCourseService;
import com.ead.authUser.services.UserService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    @Autowired
    UserService userService;

    @Autowired
    CourseClient courseClient;
    
    @Autowired
    UserCourseService userCourseService;

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseDto>> getAllCoursesByUser (@PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
    @PathVariable UUID userId) {
        
        return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllCoursesByUser(userId, pageable));                  
    }
    
    @GetMapping("/users/{userId}/courses/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable UUID userId,
                                                                @RequestBody @Valid UserCourseDto userCourseDto) {


        Optional<UserModel> userModelOptional = userService.findById(userId);

        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        
        if (userCourseService.existsByYserAndCourseId(userModelOptional.get(), userCourseDto.getCourseId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Subscription already exists!");
        }
        
        UserCourseModel userCourseModel = userCourseService.save(userModelOptional.get().convertToUserCourseModel(userCourseDto.getCourseId()));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(userCourseModel);
    }
}
