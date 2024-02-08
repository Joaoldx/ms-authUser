package com.ead.authUser.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ead.authUser.models.UserModel;

public interface UserService {

    List<UserModel> findAll();
    
    Page<UserModel> findAll(Pageable pageable);

    Optional<UserModel> findById(UUID userId);

    void save(UserModel userModel);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);    
}
