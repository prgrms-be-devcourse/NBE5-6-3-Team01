package com.grepp.synapse4.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/admin/**")
                .csrf(csrf -> csrf.disable())
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/signin", "/admin/signup", "/img/**", "/css/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .anyRequest().hasRole("ADMIN")
                );


        http
                .formLogin(auth -> auth
                        .loginPage("/admin/signin")
                        .loginProcessingUrl("/admin/signin")
                        .defaultSuccessUrl("/admin/users", true)
                        .failureUrl("/admin/signin?error=true")
                        .usernameParameter("userAccount")
                        .passwordParameter("password")
                        .permitAll()
                );

        http
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        http
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint("/admin/signin"))
                        .accessDeniedPage("/")
                );

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

        http
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/static/**").permitAll()
                        .requestMatchers("/", "/user/signin", "/user/signup", "/user/**").permitAll()
                        .requestMatchers("/search/**").permitAll()
                        .requestMatchers("/curation/**").permitAll()
                        .requestMatchers("/restaurant/**").permitAll()
                        .requestMatchers("/ranking/**").permitAll()
                        .requestMatchers("/recommend/**").authenticated()      // gemini연결 이슈로 잠깐 켜둠
                        .requestMatchers("/meetings/**").authenticated()
                        .requestMatchers("/mypage/**").authenticated()
                        .requestMatchers("/bookmark/**").authenticated()
                        .anyRequest().permitAll()
                );


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
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}


