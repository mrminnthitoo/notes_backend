package com.mrminnthitoo.notes_backend.security;

import com.mrminnthitoo.notes_backend.models.entities.User;
import com.mrminnthitoo.notes_backend.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(username, username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return createSpringSecurityUser(user);
    }

    private CustomUserDetails createSpringSecurityUser(User user) {

    List<GrantedAuthority> grantedAuthorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
    return new CustomUserDetails(user.getId(), user.getUsername(), user.getEmail(),
            user.getPassword(),
            grantedAuthorities);
}
}
