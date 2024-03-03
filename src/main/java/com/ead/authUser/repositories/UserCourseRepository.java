package com.ead.authUser.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ead.authUser.models.UserCourseModel;
import com.ead.authUser.models.UserModel;

public interface UserCourseRepository extends JpaRepository<UserCourseModel, UUID>{

    boolean existsByUserAndCourseId(UserModel usermodel, UUID courseId);

    @Query(value = "select * from tb_users_courses where user_course_id = :userId", nativeQuery = true)
    List<UserCourseModel> findAllUsersCourseIntoUser(@Param("userId") UUID courseId);
}
