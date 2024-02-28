package com.ll.spring_jwt.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collector;

@RestController
public class MainController {

    @GetMapping("/")
    public String mainP(){

        //JWT자체가 세션을 STATELESS한 상태로 관리하긴 하지만, JWT필터를 통과한 순간 일시적으로 세션을 만듬, 그래서 SecurityContextHolder.getContext().getAuthentication().getName();로 사용자 이름 확인 가능
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection< ? extends GrantedAuthority > authorities = authentication.getAuthorities();
        Iterator< ? extends GrantedAuthority > iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        return "mainPage" + username + role;
    }
}
