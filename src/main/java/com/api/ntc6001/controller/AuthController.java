package com.api.ntc6001.controller;
import com.api.ntc6001.model.dto.AuthRequestDto;
import com.api.ntc6001.model.dto.AuthResponseDto;
import com.api.ntc6001.model.dto.RegisterUserRequestDto;
import com.api.ntc6001.model.entity.Mype;
import com.api.ntc6001.model.entity.Users;
import com.api.ntc6001.service.IMype;
import com.api.ntc6001.service.JwtUtilService;
import com.api.ntc6001.service.impl.IMypeImpl;
import com.api.ntc6001.service.impl.IUserImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private IMypeImpl iMype;

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthRequestDto authRequestDto) {

        try {
            //1. Gestion authenticationManager
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequestDto.getCorreo(), authRequestDto.getPassword()
            ));

            //2. Validar el usuario en la bd
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(authRequestDto.getCorreo());
            Users userModel = userRepository.findByEmail(authRequestDto.getCorreo());

            //3. Generar token
            String jwt = this.jwtUtilService.generateToken(userDetails);
            String refreshToken = this.jwtUtilService.generateRefreshToken(userDetails, userModel.getURole());

            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setToken(jwt);
            authResponseDto.setRefreshToken(refreshToken);

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
                authResponseDto.setRefreshToken(newRefreshToken);

                return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
            }


        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error refresh token:::" + e.getMessage());
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterUserRequestDto registerUserRequestDto){
//        log.info(":::: correo:"+registerUserRequestDto.getCorreo()+" pass: "+ registerUserRequestDto.getPassword());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Users usersFind = userRepository.findByEmail(registerUserRequestDto.getCorreo());
        if(usersFind != null){
            return ResponseEntity.notFound().build();
        }
        Users user = new Users();
        user.setUCorreo(registerUserRequestDto.getCorreo());
        user.setUPassword(passwordEncoder.encode(registerUserRequestDto.getPassword()));
        user.setURole(registerUserRequestDto.getRol());

        Users userSave = userRepository.save(user);

//        log.info("IDDD::::: "+userSave.getIduser().toString());
//        log.info("SECTORRRR::::: "+registerUserRequestDto.getDireccion());
        if(user.getURole().equals("MYPE")){
            Mype mype = new Mype();
            mype.setMNit(registerUserRequestDto.getNit());
            mype.setMRazonSocial(registerUserRequestDto.getRazonsocial());
            mype.setMDireccion(registerUserRequestDto.getDireccion());
            mype.setMCorreo(registerUserRequestDto.getCorreo());
            mype.setMTelefono(registerUserRequestDto.getTelefono());
            mype.setMSector(registerUserRequestDto.getSector());
            mype.setMTipoEmpresa(registerUserRequestDto.getTipoempresa());
            mype.setUser_iduser(userSave.getIduser());
            Mype my = iMype.save(mype);
//            log.info("NITTTTT::::: "+my.getMNit());
        }

        try {
            //1. Gestion authenticationManager
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    registerUserRequestDto.getCorreo(), registerUserRequestDto.getPassword()
            ));

            //2. Validar el usuario en la bd
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(registerUserRequestDto.getCorreo());
            Users userModel = userRepository.findByEmail(registerUserRequestDto.getCorreo());

            //3. Generar token
            String jwt = this.jwtUtilService.generateToken(userDetails);
            String refreshToken = this.jwtUtilService.generateRefreshToken(userDetails, userModel.getURole());

            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setToken(jwt);
            authResponseDto.setRefreshToken(refreshToken);

            return new ResponseEntity<AuthResponseDto>(authResponseDto, HttpStatus.OK);

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Error Authetication:::" + e.getMessage());
        }

    }

    @PostMapping("/error")
    public String error(){
        return "here!!!";
    }

    @GetMapping("/health")
    public String getPreguntaHealth(){
        log.info("Hereee!!!!:::");
        return "ok";
    }
}
