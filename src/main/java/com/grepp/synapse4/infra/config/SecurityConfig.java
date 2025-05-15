package com.grepp.synapse4.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/img/**", "/css/**").permitAll()
                .requestMatchers("/", "/user/**").permitAll()
                .requestMatchers("/", "/search/**").permitAll()
                .requestMatchers("/", "/curation/**").permitAll()
                .requestMatchers("/", "/restaurant/**").permitAll()
                .requestMatchers("/", "/recommend/**").permitAll()      // gemini연결 이슈로 잠깐 켜둠
                .requestMatchers("/mypage/**").authenticated()
                .anyRequest().authenticated()
            );

        http.csrf(csrf -> csrf.disable());

        http
            .formLogin(auth -> auth
                .loginPage("/user/signin")
                .loginProcessingUrl("/user/signin")
                .defaultSuccessUrl("/", true)
                .failureUrl("/user/signin?error=true")
                .usernameParameter("userAccount")
                .passwordParameter("password")
                .permitAll()
            );

        http
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


