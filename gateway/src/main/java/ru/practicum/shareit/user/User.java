package ru.practicum.shareit.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "email")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long id;
    @NotBlank
    private String name;
    private String login;
    @NotBlank
    @Email
    private String email;
}
