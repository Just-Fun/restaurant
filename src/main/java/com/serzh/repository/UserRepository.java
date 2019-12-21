package com.serzh.repository;

import com.serzh.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    String USERS_BY_NAME_CACHE = "usersByName";
    String USERS_BY_EMAIL_CACHE = "usersByEmail";
    String USERS_CACHE = "users";


}
