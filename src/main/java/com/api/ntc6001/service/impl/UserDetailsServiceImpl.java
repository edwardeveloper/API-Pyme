package com.api.ntc6001.service.impl;

import com.api.ntc6001.dao.UserDao;
import com.api.ntc6001.model.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao iUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Call Database to validate
        Users userModel = this.iUserRepository.findByEmail(username);
        if(userModel == null) {
            throw  new UsernameNotFoundException(username);
        }
        return new User(userModel.getUCorreo(), userModel.getUPassword(), new ArrayList<>());
    }
}
