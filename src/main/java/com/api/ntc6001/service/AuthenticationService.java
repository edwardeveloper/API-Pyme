package com.api.ntc6001.service;

import com.api.ntc6001.dao.UserDao;
import com.api.ntc6001.model.dto.RegisterUserDto;
import com.api.ntc6001.model.entity.Users;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {
    private final UserDao userRepository;
    private final BCryptPasswordEncoder cryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserDao userRepository,
            AuthenticationManager authenticationManager,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.cryptPasswordEncoder = passwordEncoder;
    }

    public Users signup(RegisterUserDto input) {
        var user = new Users();
        user.setUCorreo(input.getPCorreo());
        user.setUPassword(cryptPasswordEncoder.encode(input.getPPassword()));

        return userRepository.save(user);
    }

    public Users authenticate(Users input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUCorreo(),
                        input.getUPassword()
                )
        );

        return userRepository.findByEmail(input.getUCorreo());// .orElseThrow();
    }

    public List<Users> allUsers() {
        List<Users> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}