package com.ll.spring_jwt.repository;


import com.ll.spring_jwt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository< UserEntity, Long > {
}
