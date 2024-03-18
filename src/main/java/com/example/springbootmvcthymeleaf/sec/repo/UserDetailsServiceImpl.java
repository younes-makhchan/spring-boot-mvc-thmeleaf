package com.example.springbootmvcthymeleaf.sec.repo;

import com.example.springbootmvcthymeleaf.sec.AccountService;
import com.example.springbootmvcthymeleaf.sec.entities.AppRole;
import com.example.springbootmvcthymeleaf.sec.entities.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private  final AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser=accountService.loadUserByUsername(username);
        if(appUser==null) throw  new UsernameNotFoundException(String.format("User %s not found",username));
        String[] roles=appUser.getRoles().stream().map(AppRole::getRole).toArray(String[]::new);
        UserDetails userDetails = User
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(roles)
                .build();
        return userDetails;
    }
}
