package com.ead.authUser.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ead.authUser.models.UserCourseModel;

public interface UserCourseRepository extends JpaRepository<UserCourseModel, UUID>{
    
}
