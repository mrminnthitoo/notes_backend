package com.mrminnthitoo.notes_backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String getHash(String plainPassword){
        return this.passwordEncoder.encode(plainPassword);
    }

}
