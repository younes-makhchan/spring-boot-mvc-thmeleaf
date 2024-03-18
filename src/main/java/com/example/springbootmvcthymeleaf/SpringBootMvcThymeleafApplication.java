package com.example.springbootmvcthymeleaf;

import com.example.springbootmvcthymeleaf.entities.Patient;
import com.example.springbootmvcthymeleaf.repositories.PatientRepository;
import com.example.springbootmvcthymeleaf.sec.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class SpringBootMvcThymeleafApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMvcThymeleafApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return  args -> {

            patientRepository.save(
                    new Patient(null,"Hassan",new Date(),false,122)
            );
//            patientRepository.save(
//                    new Patient(null,"Mohammed",new Date(),true,321)
//            );
//            patientRepository.save(
//                    new Patient(null,"Yassmine",new Date(),true,655)
//            );
//            patientRepository.save(
//                    new Patient(null,"Hanaae",new Date(),false,332)
//
//            );
//            patientRepository.save(
//                    new Patient(null,"Hassan",new Date(),false,122)
//            );
//            patientRepository.save(
//                    new Patient(null,"Mohammed",new Date(),true,321)
//            );
//            patientRepository.save(
//                    new Patient(null,"Yassmine",new Date(),true,655)
//            );
//            patientRepository.save(
//                    new Patient(null,"Hanaae",new Date(),false,532)
//
//            );

            patientRepository.findAll().forEach((p)->{
                System.out.println(p.getNom());
            });
        };
    }

//    @Bean
    CommandLineRunner commandLineRunner1(JdbcUserDetailsManager jdbcUserDetailsManager){
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        return args -> {
            UserDetails user=jdbcUserDetailsManager.loadUserByUsername("user11");
            if(user==null){
                jdbcUserDetailsManager.createUser(
                        User.withUsername("user11")
                                .password(passwordEncoder.encode("1234"))
                                .roles("USER")
                                .build()
                );
            }
            user=jdbcUserDetailsManager.loadUserByUsername("user12");
            if(user==null){

                jdbcUserDetailsManager.createUser(
                        User.withUsername("user12")
                                .password(passwordEncoder.encode("1234"))
                                .roles("USER", "ADMIN")
                                .build()
                );
            }


        };
    }
//    @Bean
    CommandLineRunner commandLineRunner2(AccountService accountService){
        return   args -> {
            accountService.addNewRole("USER");
            accountService.addNewRole("ADMIN");
            accountService.addNewUser("user1","1234","user1@gmail.com","1234");
            accountService.addNewUser("user2","1234","user2@gmail.com","1234");
            accountService.addNewUser("admin","1234","admin@gmail.com","1234");


            accountService.addRoleToUser("user1","USER");
            accountService.addRoleToUser("user2","USER");
            accountService.addRoleToUser("admin","USER");
            accountService.addRoleToUser("admin","ADMIN");

        };

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
