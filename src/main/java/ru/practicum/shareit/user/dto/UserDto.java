package ru.practicum.shareit.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.validation.constraints.Email;
import java.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

}
