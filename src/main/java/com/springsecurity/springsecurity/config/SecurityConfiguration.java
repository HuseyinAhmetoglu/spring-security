package com.springsecurity.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.formLogin();
        http
                .authorizeHttpRequests()
                .requestMatchers("/index").permitAll()
                .requestMatchers("/dashboard").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/admin").hasAnyRole("ADMIN");
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

//        authenticationManagerBuilder.inMemoryAuthentication()
//                .withUser("admin").password("{noop}1234").roles("admin")
//                .and()
//                .withUser("user").password("{noop}1234").roles("user");

        authenticationManagerBuilder.userDetailsService(userDetailsService);

        return authenticationManagerBuilder.build();
    }
}

