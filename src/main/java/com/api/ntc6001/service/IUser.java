package com.api.ntc6001.service;

import com.api.ntc6001.model.entity.Users;

public interface IUser{
    Users save(Users users);

    Users findById(Long id);
    Users findByEmail(String email);

//    void delete(User user);

//    List<User> getUser();
}
