package com.iqbrave.iqbrave_lms.config;

import com.iqbrave.iqbrave_lms.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private com.iqbrave.iqbrave_lms.security.JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private com.iqbrave.iqbrave_lms.exception.CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                        .requestMatchers("/api/courses/**").hasAnyRole("INSTRUCTOR", "ADMIN")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/instructor/**").hasRole("INSTRUCTOR")
                        // Enrollments
                        .requestMatchers(HttpMethod.POST, "/api/enrollments/enroll/**").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/enrollments/student/me").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/enrollments/student/**").hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers(HttpMethod.GET, "/api/enrollments/course/**").hasAnyRole("INSTRUCTOR", "ADMIN")
                        // Lesson Progress
                        .requestMatchers(HttpMethod.POST, "/api/progress/complete").hasRole("STUDENT")
                        .requestMatchers(HttpMethod.GET, "/api/progress/student/**").hasAnyRole("STUDENT", "INSTRUCTOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/progress/student/**/course/**/percentage")
                        .hasAnyRole("STUDENT", "INSTRUCTOR", "ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\":\"Unauthorized\"}");
                        })
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable());

        http.addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
