package com.example.springbootmvcthymeleaf;

import com.example.springbootmvcthymeleaf.entities.Patient;
import com.example.springbootmvcthymeleaf.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
}
