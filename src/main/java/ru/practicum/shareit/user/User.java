package ru.practicum.shareit.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "email")
public class User {
    private long id;
    @NotBlank
    private String name;
    private String login;
    @NotBlank
    private String email;
}
