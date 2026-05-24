package com.Sistema.DeporPlaza.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SeguridadConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                return http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(
                                                                "/css/**",
                                                                "/js/**",
                                                                "/img/**",
                                                                "/error404/**",
                                                                "/error500/**",
                                                                "/",
                                                                "/login/**")
                                                .permitAll()
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .anyRequest().hasRole("ADMIN"))
                                // Aca configuramos el form para el login, osea el login personalizado que
                                // creamos login.html enmi caso
                                .exceptionHandling(exception -> exception
                                                .accessDeniedPage("/error403"))
                                .formLogin(form -> form
                                                // le damos el url
                                                .loginPage("/login")
                                                // Le decimos a donde nos dirige si esta bien
                                                .defaultSuccessUrl("/inicio", true)
                                                // Esto dice que todos pueden ver esta pagina
                                                .permitAll())
                                .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll())
                                .build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}
