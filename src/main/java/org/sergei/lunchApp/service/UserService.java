package org.sergei.lunchApp.service;

import lombok.RequiredArgsConstructor;
import org.sergei.lunchApp.dto.User;
import org.sergei.lunchApp.model.UserEntity;
import org.sergei.lunchApp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(User user) {
        UserEntity userEntity =UserEntity.builder()
                .userName(user.getUserName())
                .password(user.getPassword())
                .name(user.getName())
                .surname(user.getSurname())
                .balance(user.getBalance())
                .userRole(user.getUserRole())
                .createdAt(user.getCreatedAt())
                .build();

        userRepository.save(userEntity);
    }

    public List<User> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();

        return users.stream().map(this::mapToUser).toList();
    }

    private User mapToUser(UserEntity userEntity) {
        return User.builder()
                .uuid(userEntity.getUuid())
                .userName(userEntity.getUserName())
                .password(userEntity.getPassword())
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .balance(userEntity.getBalance())
                .userRole(userEntity.getUserRole())
                .createdAt(userEntity.getCreatedAt())
                .build();
    }
}
