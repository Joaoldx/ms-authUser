package com.ead.authUser.dtos;

import java.util.UUID;

import com.ead.authUser.validation.UsernameConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    private UUID userId;

    @NotBlank(groups = UserView.RegistrationPost.class)
    @Size(min = 4, max = 50, groups = UserView.RegistrationPost.class)
    @UsernameConstraint(groups = UserView.RegistrationPost.class)
    @JsonView(UserView.RegistrationPost.class)
    private String username;
    
    @NotBlank(groups = UserView.RegistrationPost.class)
    @Email
    @JsonView(UserView.RegistrationPost.class)
    private String email;
    
    @NotBlank(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
    @Size(min = 6, max = 20, groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    private String password;
    
    @NotBlank(groups = UserView.PasswordPut.class)
    @Size(min = 6, max = 20, groups = UserView.PasswordPut.class)
    @JsonView(UserView.PasswordPut.class)
    private String oldPassword;
    
    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    private String fullname;
    
    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    private String phoneNumber;
    
    @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
    private String cpf;

    @NotBlank(groups = UserView.ImagePut.class)
    @JsonView({UserView.RegistrationPost.class, UserView.ImagePut.class})
    private String imageUrl;
}
