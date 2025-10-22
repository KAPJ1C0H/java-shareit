package ru.practicum.shareit.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserUpdateDto;
import ru.practicum.shareit.user.exception.OnValidEmail;
import ru.practicum.shareit.user.exception.ParamValidationError;
import ru.practicum.shareit.user.exception.UserNotFoundException;

import java.util.Collection;

@Service
@Slf4j
public class UserServiceIml implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Collection<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        String email = user.getEmail().trim().toLowerCase();
        log.info(email);
        if (userRepository.existsByEmail(email)) throw new OnValidEmail("Email is already exist");
        if (!user.getEmail().contains("@")) {
            throw new ParamValidationError("НЕ валидный email");
        }
        user.setEmail(user.getEmail().toLowerCase());
        return userRepository.save(user);
    }


    @Override
    public User update(UserUpdateDto userUpdateDto) {
        User existingUser = userRepository.findById(userUpdateDto.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (userUpdateDto.getEmail() != null) {
            if (!userUpdateDto.getEmail().isBlank()) {
                if (userRepository.existsByEmail(userUpdateDto.getEmail().toLowerCase())) {
                    throw new OnValidEmail("Email is already exists");
                }
                existingUser.setEmail(userUpdateDto.getEmail());
            }
        }
        if (userUpdateDto.getName() != null) {
            if (!userUpdateDto.getName().isBlank())
                existingUser.setName(userUpdateDto.getName());
        }
        if (existingUser.getEmail() != null) {
            if (!existingUser.getEmail().contains("@")) {
                throw new ParamValidationError("НЕ вылидный email");
            }
        }
        return userRepository.save(existingUser);
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found"));
    }
}
