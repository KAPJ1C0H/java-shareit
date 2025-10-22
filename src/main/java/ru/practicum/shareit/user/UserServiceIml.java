package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.dto.UserUpdateDto;
import ru.practicum.shareit.user.exception.ParamValidationError;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserServiceIml implements UserService {
    private final UserRepository userRepository;

    @Override
    public Collection<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User save(User user) {
        if (!user.getEmail().contains("@")) {
            throw new ParamValidationError("НЕ вылидный email");
        }
        return userRepository.save(user);
    }

    @Override
    public User update(UserUpdateDto userUpdateDto) {
        User user = UserMapper.fromUpdateDto(userUpdateDto);
        if (user.getEmail() != null) {
            if (!user.getEmail().contains("@")) {
                throw new ParamValidationError("НЕ вылидный email");
            }
        }
        return userRepository.update(user);
    }

    @Override
    public void delete(long id) {
        boolean userIsDeleteOrNo = userRepository.delete(id);
        if (!userIsDeleteOrNo) throw new RuntimeException("in correct user id: " + id);
    }

    @Override
    public User getById(long id) {
        return userRepository.getById(id);
    }
}
