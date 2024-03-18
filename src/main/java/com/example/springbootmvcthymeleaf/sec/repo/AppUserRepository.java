package com.example.springbootmvcthymeleaf.sec.repo;

import com.example.springbootmvcthymeleaf.sec.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,String> {
    AppUser findByUsername(String username);
}
