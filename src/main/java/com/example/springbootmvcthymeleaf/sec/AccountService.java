package com.example.springbootmvcthymeleaf.sec;


import com.example.springbootmvcthymeleaf.sec.entities.AppRole;
import com.example.springbootmvcthymeleaf.sec.entities.AppUser;

public interface AccountService {

    AppUser addNewUser(String username, String password, String email, String confirmPassword);
    AppRole addNewRole(String role);

    void addRoleToUser(String username,String role);
    void removeRoleToUser(String username,String role);
    AppUser loadUserByUsername(String username);

}
