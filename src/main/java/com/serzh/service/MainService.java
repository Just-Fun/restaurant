package com.serzh.service;

import org.springframework.transaction.annotation.Transactional;

public interface MainService {

    void save();

    @Transactional
    void get(Integer id);

    @Transactional
    void update(Integer id);
}