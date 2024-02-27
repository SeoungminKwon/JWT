package com.ll.spring_jwt.service;

import com.ll.spring_jwt.dto.CustomUserDetails;
import com.ll.spring_jwt.entity.UserEntity;
import com.ll.spring_jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity findUser = userRepository.findByUsername(username);
        if (findUser != null) {
            return new CustomUserDetails(findUser);
        }


        return null;
    }
}
