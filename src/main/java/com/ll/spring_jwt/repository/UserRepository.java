package com.ll.spring_jwt.repository;


import com.ll.spring_jwt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository< UserEntity, Long > {

    Boolean existsByUsername(String username);

    //username을 받아 DB테이블에서 회원을 조회
    UserEntity findByUsername(String username);
}
