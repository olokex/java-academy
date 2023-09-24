package com.devcors.javaacademy.lesson6.service;

import com.devcors.javaacademy.lesson6.data.entity.User;
import com.devcors.javaacademy.lesson6.data.entity.enums.UserRole;
import com.devcors.javaacademy.lesson6.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("username not found");
        }
        return user;
    }

//    @PostConstruct
//    void init() {
//        userRepository.save(User.builder()
//                    .email("email@email.cz")
//                    .username("admin")
//                    .role(UserRole.ADMIN)
//                    .password(passwordEncoder.encode("password"))
//                    .build());
//        userRepository.save(User.builder()
//                    .email("email@email.cz")
//                    .username("user")
//                    .role(UserRole.USER)
//                    .password(passwordEncoder.encode("password"))
//                    .build());
//    }
}
