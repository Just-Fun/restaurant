package com.serzh.cache;

import com.serzh.domain.User;

import java.util.List;

public interface UserServiceCache {

    User create(User user);

    User get(Long id);

    User create(String name, String email);

    List<User> findAll();

    User createOrReturnCached(User user);

    User createAndRefreshCache(User user);

    void delete(Long id);

    void deleteAndEvict(Long id);
}
