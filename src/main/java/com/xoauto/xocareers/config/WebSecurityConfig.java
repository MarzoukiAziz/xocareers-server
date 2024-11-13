package com.xoauto.xocareers.config;

import com.xoauto.xocareers.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;


@Configuration
public class WebSecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    private final CustomCorsConfiguration customCorsConfiguration;



    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter, CustomCorsConfiguration customCorsConfiguration) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.customCorsConfiguration = customCorsConfiguration;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return provider;
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(c -> c.configurationSource(customCorsConfiguration))
                .authorizeHttpRequests(requestConfigurer -> requestConfigurer
                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        .requestMatchers(antMatcher("/admin/**")).hasAuthority("ROLE_ADMIN")
                        .requestMatchers(antMatcher("/user/**")).hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                        .requestMatchers(antMatcher("/job-offer/admin/**")).hasAuthority("ROLE_ADMIN")
                        .requestMatchers(antMatcher("/candidature/admin/**")).hasAuthority("ROLE_ADMIN")
                        .requestMatchers(antMatcher("/candidature/**")).hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")

                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(customUserDetailsService)
                .sessionManagement(sessionConfigurer -> sessionConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );


        return httpSecurity.build();
    }

}