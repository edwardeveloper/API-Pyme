package com.api.ntc6001.controller;
import com.api.ntc6001.model.dto.AuthRequestDto;
import com.api.ntc6001.model.dto.AuthResponseDto;
import com.api.ntc6001.model.entity.Users;
import com.api.ntc6001.service.JwtUtilService;
import com.api.ntc6001.service.impl.IUserImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Controller
@CrossOrigin("*")
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private IUserImpl userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthRequestDto authRequestDto) {

        try {
            //1. Gestion authenticationManager
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequestDto.getUser(), authRequestDto.getPassword()
            ));

            //2. Validar el usuario en la bd
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(authRequestDto.getUser());
            Users userModel = userRepository.findByEmail(authRequestDto.getUser());

            //3. Generar token
            String jwt = this.jwtUtilService.generateToken(userDetails);
            String refreshToken = this.jwtUtilService.generateRefreshToken(userDetails, userModel.getURole());

            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setToken(jwt);
//            authResponseDto.setRefreshToken(refreshToken);

            return new ResponseEntity<AuthResponseDto>(authResponseDto, HttpStatus.OK);

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Error Authetication:::" + e.getMessage());
        }

    }


    @PostMapping("/refresh")
    public ResponseEntity<?> auth(@RequestBody Map<String, String>  request) {
        String refreshToken = request.get("refreshToken");
        try {
            String username = jwtUtilService.extractUsername(refreshToken);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Users userModel = userRepository.findByEmail(username);

            if(jwtUtilService.validateToken(refreshToken, userDetails)) {
                String newJwt = jwtUtilService.generateToken(userDetails, userModel.getURole());
                String newRefreshToken = jwtUtilService.generateRefreshToken(userDetails, userModel.getURole());

                AuthResponseDto authResponseDto = new AuthResponseDto();
                authResponseDto.setToken(newJwt);
//                authResponseDto.setRefreshToken(newRefreshToken);

                return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
            }


        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error refresh token:::" + e.getMessage());
        }

    }

    @PostMapping("/error")
    public String error(){
        return "here!!!";
    }
}
