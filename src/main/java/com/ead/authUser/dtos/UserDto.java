package com.ead.authUser.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @NotBlank
    private String username;
    
    @NotBlank
    private String email;
    
    @NotBlank
    private String password;
    
    @NotBlank
    private String oldPassword;
    
    @NotBlank
    private String fullname;
    
    @NotBlank
    private String phoneNumber;
    
    @NotBlank
    private String cpf;
    
    private String imageUrl;
}
