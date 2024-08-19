//package com.chikacow.pet_project.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests(authorizeRequests -> {
//                        authorizeRequests
//                                .requestMatchers("/admin/**").authenticated() // Secure specific path
//                                .anyRequest().permitAll(); // Allow other paths without authentication
//                    }
//                )
//                .formLogin(formLogin -> {
//                            formLogin
//                                    .loginPage("/login")
//                                    .permitAll();
//                        }
//                )
//                .logout(logout -> {
//                            logout
//                                    .logoutUrl("/logout")
//                                    .permitAll();
//                        }
//                );
//
//        return http.build();
//    }
//}
