package com.devcors.javaacademy.lesson6.data.repository;

import com.devcors.javaacademy.lesson6.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
