package com.ead.authUser.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ead.authUser.models.UserModel;

public interface UserService {

    List<UserModel> findAll();

    Optional<UserModel> findById(UUID userId);

    UserModel save(UserModel userModel);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
    
}
