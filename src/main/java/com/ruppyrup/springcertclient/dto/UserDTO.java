package com.ruppyrup.springcertclient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotBlank(message = "username can't be empty")
    @Size(min = 2)
    private String password;

    @NotBlank(message = "password can't be empty")
    @Size(min = 8)
    @Pattern(regexp = "^A-Za-z0-9]+")
    private String username;
}
