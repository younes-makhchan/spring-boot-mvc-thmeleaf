package com.example.springbootmvcthymeleaf.sec;


import com.example.springbootmvcthymeleaf.sec.repo.UserDetailsServiceImpl;
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
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private  final UserDetailsServiceImpl UserDetailsServiceImpl;
    private final  PasswordEncoder passwordEncoder;
//    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user=User.builder()
                .username("lina")
                .password(passwordEncoder.encode("1234"))
                .roles("USER").build();
        UserDetails user2=User.builder()
                .username("younes")
                .password(passwordEncoder.encode("1234"))
                .roles("USER","ADMIN").build();
        return new InMemoryUserDetailsManager(user,user2);

    }
    @Bean
    public UserDetailsService customeUserDetailsService(){
        return UserDetailsServiceImpl;
    }
//    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource){
        return  new JdbcUserDetailsManager(dataSource);


    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws  Exception{
        httpSecurity.formLogin(AbstractAuthenticationFilterConfigurer::permitAll)//should be here if authorizeHttpRequests exists
                .authorizeHttpRequests(requestMatcherRegistry -> {
                    requestMatcherRegistry.requestMatchers("/","/webjars/**").permitAll();

                    requestMatcherRegistry.requestMatchers("/admin/**")//alow this for admin
                            .hasRole( "ADMIN" );
                    requestMatcherRegistry.requestMatchers(  "/user/**" ).hasRole("USER");//allow this for user
                    requestMatcherRegistry.anyRequest().authenticated();//any request should be auth
                 }
                ).exceptionHandling(exception->{
                    exception.accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.sendRedirect("/403");
                    });
                })
                .userDetailsService(UserDetailsServiceImpl);


        return  httpSecurity.build();
    }
}
