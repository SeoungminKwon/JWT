package com.ll.spring_jwt.jwt;

import com.ll.spring_jwt.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    //Authentication를 진행하기 위한 attemptAuthentication을 override해줘야한다.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //클라이언트 요청에서 username, password 추출
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        //시큐리티에서 username, password를 검증하기 위해서는 token에 담아야함
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, null);

        //token에 담은 정보를 검증을 위해 AuthenticationManager에 전달
        return authenticationManager.authenticate(token);
    }

    //authenticationManager에서 검증 성공
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        //UserDetails
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();

        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority > authorities = authResult.getAuthorities();
        Iterator< ? extends GrantedAuthority > iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(username, role, 60*60*10L);
        response.addHeader("Authorization", "Bearer " + token);
        //주의 Bearer(띄어쓰기) - 띄어쓰기 주의 할것
        //HTTP 인증방식은 RFC7235 정의에 따라 아래 인증 헤더 형태를 가져가야한다.
        //타입 인증토큰 : Authoriztion
        //인증토큰 String : Authorization: Bearer
    }

    //authenticationManager에서 검증 실패
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //로그인 실패 401응답 코드 반환
        response.setStatus(401);
    }
}
