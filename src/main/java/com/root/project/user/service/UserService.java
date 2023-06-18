package com.root.project.user.service;

import com.root.project.user.User;
import com.root.project.user.dto.UserDto;
import com.root.project.user.model.request.UserRm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {
    User createUser(UserDto user);

    User updateUser(UserDto userDetails);

    User verfiedUser(String username);

    Optional<User> findUserByUsernameOrEmail(String username);
}
