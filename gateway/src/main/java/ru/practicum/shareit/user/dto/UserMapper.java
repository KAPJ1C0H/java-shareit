package ru.practicum.shareit.user.dto;

import ru.practicum.shareit.user.User;

public class UserMapper {

    public static UserDto toBookingInfoDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static UserDto toBookingInfoDtoFromUpdateDto(UserUpdateDto userUpdateDto) {
        return UserDto.builder()
                .id(userUpdateDto.getId())
                .name(userUpdateDto.getName())
                .email(userUpdateDto.getEmail())
                .build();
    }

    public static User fromUpdateDto(UserUpdateDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }
}
