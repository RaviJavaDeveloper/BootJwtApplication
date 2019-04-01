package com.ravi.services;

import com.ravi.models.User;
import com.ravi.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optional = userDao.findByEmail(email);
        optional.orElseThrow(()-> new UsernameNotFoundException("Invalid credentials"));
        return optional
                .map(CustomUserDetails::new).get();
    }
}
