package com.gbossoufolly.blogapi.payloads;

import com.gbossoufolly.blogapi.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private int id;

    @NotEmpty(message = "Enter your name, please.")
    @Size(min = 4, message = "Username must be min of 4 characters")
    private String name;

    @Email(message = "Email address is not valid")
    private String email;

    @NotEmpty(message = "Enter your password, please.")
    @Size(min = 3, max = 10, message = "Password must be min of 3 characters and max of 10 characters")
    private String password;
    @NotEmpty
    private String about;

}
