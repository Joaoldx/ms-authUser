package com.ead.authUser.services;

import java.util.UUID;

import com.ead.authUser.models.UserCourseModel;
import com.ead.authUser.models.UserModel;

public interface UserCourseService {

    boolean existsByYserAndCourseId(UserModel userModel, UUID courseId);

    UserCourseModel save(UserCourseModel convertToUserCourseModel);
    
}
