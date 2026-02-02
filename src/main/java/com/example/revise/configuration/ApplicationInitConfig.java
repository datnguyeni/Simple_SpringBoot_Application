package com.example.revise.configuration;

import com.example.revise.entity.User;
import com.example.revise.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;
    @Autowired
    public ApplicationInitConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // dc chay khi chuong trinh sac len - co the sac nhieu lan
    // ad admin
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
       return application -> {
           //
           if(userRepository.findByUsername("admin").isEmpty()){
                User user = (User) User.builder()
                       .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .role("ROLE_ADMIN")
                       .build();
               User savedUser = userRepository.save(user);
               log.warn("Created admin user : admin, admin");
           }
       };
    }



}
