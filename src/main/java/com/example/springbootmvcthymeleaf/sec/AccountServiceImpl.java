package com.example.springbootmvcthymeleaf.sec;


import com.example.springbootmvcthymeleaf.sec.entities.AppRole;
import com.example.springbootmvcthymeleaf.sec.entities.AppUser;
import com.example.springbootmvcthymeleaf.sec.repo.AppRoleRepository;
import com.example.springbootmvcthymeleaf.sec.repo.AppUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private  final AppUserRepository userRepository;
    private  final AppRoleRepository roleRepository;
    private  final PasswordEncoder passwordEncoder;


    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser user =userRepository.findByUsername(username);
        if(user!=null) throw new RuntimeException("Username already exsits ");
        if(!password.equals(confirmPassword))throw  new RuntimeException("Password doesn't match the confirm password");

        user=AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();
        userRepository.save(user);
        return user;
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole appRole= roleRepository.findById(role).orElse(null);
        if(appRole!=null) throw new RuntimeException("role Already Exists");
        appRole =AppRole.builder()
                .role(role)
                .build();
        roleRepository.save(appRole);
        return appRole;
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser user =userRepository.findByUsername(username);
        AppRole appRole= roleRepository.findById(role).orElse(null);
        if(appRole==null || user==null)throw  new RuntimeException("Role or user doesn't exists");

        user.getRoles().add(appRole);

        //userRepository.save(user)

    }
    @Override
    public void removeRoleToUser(String username, String role) {
        AppUser user =userRepository.findByUsername(username);
        AppRole appRole= roleRepository.findById(role).orElse(null);
        if(appRole==null || user==null)throw  new RuntimeException("Role or user doesn't exists");

        user.getRoles().remove(appRole);

        //userRepository.save(user)

    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
