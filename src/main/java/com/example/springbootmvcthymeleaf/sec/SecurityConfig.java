package com.example.springbootmvcthymeleaf.sec;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user=User.builder()
                .username("lina")
                .password(passwordEncoder().encode("1234"))
                .roles("USER").build();
        UserDetails user2=User.builder()
                .username("younes")
                .password(passwordEncoder().encode("1234"))
                .roles("USER","ADMIN").build();
        return new InMemoryUserDetailsManager(user,user2);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws  Exception{
        httpSecurity.formLogin(AbstractAuthenticationFilterConfigurer::permitAll)//should be here if authorizeHttpRequests exists
                .authorizeHttpRequests(requestMatcherRegistry -> {
                    requestMatcherRegistry.requestMatchers("/delete/**", "/edit/**", "/save/**")//alow this for admin
                            .hasRole( "ADMIN" );
                    requestMatcherRegistry.requestMatchers(  "/","/index/**" ).hasRole("USER");//allow this for user
                    requestMatcherRegistry.anyRequest().authenticated();//any request should be auth
                 }
                );


        return  httpSecurity.build();
    }
}
