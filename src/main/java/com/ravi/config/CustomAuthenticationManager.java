package com.ravi.config;

import com.ravi.models.User;
import com.ravi.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserDao userDao;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";

        Optional<User> optional = userDao.findByEmail(username);
        optional.orElseThrow(() -> new UsernameNotFoundException("User not found."));
        List<? extends GrantedAuthority> grantedAuthorityList = optional.get().getRoles()
                .stream().map( role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(username, password, grantedAuthorityList);
    }
}
