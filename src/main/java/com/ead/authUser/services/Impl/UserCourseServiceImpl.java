package com.ead.authUser.services.Impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ead.authUser.models.UserCourseModel;
import com.ead.authUser.models.UserModel;
import com.ead.authUser.repositories.UserCourseRepository;
import com.ead.authUser.services.UserCourseService;

@Service
public class UserCourseServiceImpl implements UserCourseService {
    
    @Autowired
    UserCourseRepository userCourseRepository;

    @Override
    public boolean existsByYserAndCourseId(UserModel userModel, UUID courseId) {
        return userCourseRepository.existsByUserAndCourseId(userModel, courseId);
    }

    @Override
    public UserCourseModel save(UserCourseModel userCourseModel) {
        return userCourseRepository.save(userCourseModel);
    }
}
