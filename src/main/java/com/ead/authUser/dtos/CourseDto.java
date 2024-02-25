package com.ead.authUser.dtos;

import java.util.UUID;

import com.ead.authUser.enums.CourseLevel;
import com.ead.authUser.enums.CourseStatus;

import lombok.Data;

@Data
public class CourseDto {
    
    private UUID courseId;
    private String name;
    private String description;
    private String imageUrl;
    private CourseStatus courseStatus;
    private UUID userInstructor;
    private CourseLevel courseLevel;
}
