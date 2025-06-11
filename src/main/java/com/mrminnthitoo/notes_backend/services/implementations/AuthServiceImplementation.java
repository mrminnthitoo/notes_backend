package com.mrminnthitoo.notes_backend.services.implementations;

import com.mrminnthitoo.notes_backend.helpers.Mapper;
import com.mrminnthitoo.notes_backend.models.dtos.LoginDto;
import com.mrminnthitoo.notes_backend.models.dtos.RegisterDto;
import com.mrminnthitoo.notes_backend.models.dtos.UserDto;
import com.mrminnthitoo.notes_backend.models.entities.Role;
import com.mrminnthitoo.notes_backend.models.entities.User;
import com.mrminnthitoo.notes_backend.repositories.UserRepository;
import com.mrminnthitoo.notes_backend.security.JwtService;
import com.mrminnthitoo.notes_backend.security.SecurityService;
import com.mrminnthitoo.notes_backend.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImplementation implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private Mapper mapper;

    @Override
    public UserDto register(RegisterDto registerDto) throws Exception {
        User existingUser = this.userRepository.findByEmail(registerDto.getEmail());
        if(existingUser!=null)
        {
            throw new Exception("user already existed");
        }
        else
        {
            User user = new User();
            user.setName(registerDto.getName());
            user.setUsername(String.valueOf(System.currentTimeMillis()));
            user.setEmail(registerDto.getEmail());
            user.setPassword(securityService.getHash(registerDto.getPassword()));

            Role role1 = new Role();
            role1.setName("ROLE_USER");
            user.getRoles().add(role1);

            User registeredUser = this.userRepository.save(user);

            return this.mapper.map(registeredUser, UserDto.class);

        }
    }

    @Override
    public String login(LoginDto userDto) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername() == null ? userDto.getEmail() : userDto.getUsername(), userDto.getPassword()));
        User u = userRepository.findByUsernameOrEmail(userDto.getUsername(), userDto.getEmail());
        return jwtService.generateToken(u);
    }
}
