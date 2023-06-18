package com.root.project.user.repository;


import com.root.project.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("select u from User u where u.username = ?1")
    Optional<User> findUserByUsername(String username);
    @Query("select u from User u where u.email = ?1")
    Optional<User> findUserByEmail(String email);
    @Query("select u from User u where u.username = ?1 or u.email = ?1")
    Optional<User> findUserByUsernameOrEmail(String uore);
}
