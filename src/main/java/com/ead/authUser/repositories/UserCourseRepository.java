package com.ead.authUser.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ead.authUser.models.UserCourseModel;
import com.ead.authUser.models.UserModel;

public interface UserCourseRepository extends JpaRepository<UserCourseModel, UUID>{

    boolean existsByUserAndCourseId(UserModel usermodel, UUID courseId);
}
