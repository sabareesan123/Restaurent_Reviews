package com.edstem.reataurent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig  {


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(requests -> requests
                      .requestMatchers("/swagger-ui/index.html").hasRole("USER")
                        .requestMatchers("/error").permitAll()

                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults())

                .build();
    }


    @Bean
    UserDetailsService userDetailsService() {
        var josh = User.withUsername("josh")
                .password(passwordEncoder().encode("pw")) // Encode the password for josh
                .roles("USER")
                .build();
        var rob = User.withUsername("rob")
                .password(passwordEncoder().encode("pw")) // Encode the password for rob
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(josh, rob);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}