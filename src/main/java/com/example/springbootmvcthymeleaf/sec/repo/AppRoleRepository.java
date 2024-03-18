package com.example.springbootmvcthymeleaf.sec.repo;

import com.example.springbootmvcthymeleaf.sec.entities.AppRole;
import com.example.springbootmvcthymeleaf.sec.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,String> {
}
