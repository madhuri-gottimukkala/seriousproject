package com.ucmo.lms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ucmo.lms.entity.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, String> {
    Login findByEmail(String email);

    @Query("SELECT email FROM Login LOGIN WHERE LOGIN.email = ?1")
    String findEmailByEmail(String email);

    boolean existsByEmail(String email);
}