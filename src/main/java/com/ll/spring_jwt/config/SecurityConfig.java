package com.ll.spring_jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //비밀번호를 해싱 암호화 하기 때문에 Bean으로 등록해줌
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        //csrf disable
        http
                //session을 StateLess 상태로 관리 하기 때문에 csrf 공격을 방어 안해두됨
                .csrf(
                (auth) -> auth.disable());

        //form 로그인 방식 disable
        http
                .formLogin(auth -> auth.disable());

        //httpBasic disable
        http
                .httpBasic(auth -> auth.disable());

        //경로별 인가 작업
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/", "join").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

        //중요 - JWT 방식에서는 세션을 항상 STATELESS 상태로 관리함
        //세션 설정
        http
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
