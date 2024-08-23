package com.api.ntc6001.service;

import com.api.ntc6001.dao.PymeDao;
import com.api.ntc6001.model.dto.LoginPymeDto;
import com.api.ntc6001.model.dto.RegisterPymeDto;
import com.api.ntc6001.model.entity.Pyme;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final PymeDao pymeDao;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            PymeDao pymeDao,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.pymeDao = pymeDao;
        this.passwordEncoder = passwordEncoder;
    }

    public Pyme signup(RegisterPymeDto input) {
        Pyme pyme = new Pyme();
        pyme.setPRazonSocial(input.getPRazonSocial());
        pyme.setPRut(input.getPRut());
        pyme.setPCorreo(input.getPCorreo());
        pyme.setPNit(input.getPNit());
        pyme.setPTelefono(input.getPTelefono());
        pyme.setPDireccion(input.getPDireccion());
        pyme.setPObjetoSocial(input.getPObjetoSocial());
        pyme.setPRepresentanteLegal(input.getPRepresentanteLegal());
        pyme.setPPassword(passwordEncoder.encode(input.getPPassword()));

        return pymeDao.save(pyme);
    }

    public Pyme authenticate(LoginPymeDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
        return pymeDao.findByEmail(input.getEmail());
    }
}
