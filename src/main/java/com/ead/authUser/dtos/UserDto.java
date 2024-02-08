package com.ead.authUser.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
 
    public interface UserView {
        public static interface RegistrationPost {}
        public static interface UserPut {}
        public static interface PasswordPut {}
        public static interface ImagePut {}
    }

    @NotBlank
    @JsonView(UserView.RegistrationPost.class)
    private String username;
    
    @NotBlank
    @JsonView(UserView.RegistrationPost.class)
    private String email;
    
    @NotBlank
    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    private String password;
    
    @NotBlank
    @JsonView(UserView.PasswordPut.class)
    private String oldPassword;
    
    @NotBlank
    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    private String fullname;
    
    @NotBlank
    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    private String phoneNumber;
    
    @NotBlank
    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    private String cpf;
    
    @JsonView({UserView.RegistrationPost.class, UserView.ImagePut.class})
    private String imageUrl;
}
