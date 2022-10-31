package com.gbossoufolly.blogapi.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Integer id;

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

    private Set<RoleDTO> roles = new HashSet<>();

}
