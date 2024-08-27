package com.api.ntc6001.service.impl;

import com.api.ntc6001.dao.UserDao;
import com.api.ntc6001.model.entity.Users;
import com.api.ntc6001.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class IUserImpl implements IUser {

    @Autowired
    private UserDao userDao;

    @Override
    public Users save(Users users) {
        return userDao.save(users);
    }

    @Override
    public Users findById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    public Users findByEmail(String email) {
        return userDao.findByEmail(email);
    }

//    @Override
//    public void delete(User user) {
//
//    }

//    @Override
//    public List<User> getUser() {
//        return null;
//    }
}
