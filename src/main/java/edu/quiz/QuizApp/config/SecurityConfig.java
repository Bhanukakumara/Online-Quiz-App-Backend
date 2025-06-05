package edu.quiz.QuizApp.config;

import edu.quiz.QuizApp.enums.UserRole;
import edu.quiz.QuizApp.filters.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(c->
                        c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->auth
//                        .requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                        .requestMatchers(HttpMethod.PUT, "/api/auth/user/create").hasRole(UserRole.ADMIN.name())
//                        .requestMatchers(HttpMethod.GET,"/api/auth/user/get-all").hasRole(UserRole.ADMIN.name())
//                        .requestMatchers(HttpMethod.POST, "/api/auth/course/create").hasRole(UserRole.ADMIN.name())
//                        .requestMatchers(HttpMethod.GET, "/api/auth/course/get-all").hasAnyRole(UserRole.ADMIN.name(), UserRole.TEACHER.name())
//                        .requestMatchers(HttpMethod.POST, "/api/auth/exam/create").hasAnyRole(UserRole.ADMIN.name(), UserRole.TEACHER.name())
//                        .requestMatchers(HttpMethod.GET, "/api/auth/exam/get-all").hasAnyRole(UserRole.ADMIN.name(), UserRole.TEACHER.name(), UserRole.STUDENT.name())
//                        .requestMatchers(HttpMethod.POST, "/api/auth/question/create").hasAnyRole(UserRole.ADMIN.name(), UserRole.TEACHER.name())
                        .anyRequest().permitAll())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
