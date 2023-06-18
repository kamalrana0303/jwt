package com.root.project.security;

import com.root.project.role.Authority;
import com.root.project.role.RoleName;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.sql.DataSource;

/**
 * @author Kamal Mohan Singh Rana
 * */
@EnableWebSecurity
@RequiredArgsConstructor
@Order(2)
public class UserSecurityConfig {
    private final DataSource dataSource;
    private final UserDetailsService userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;
    private final TOTPAuthenticationFilter totpAuthenticationFilter;
    private final JwtUtils jwtUtils;
    private static final String REGISTER_URL = "/register";
    private static final String LOGIN_URL = "/api/login";
    private static final String LOGIN_FAIL_URL = LOGIN_URL+"?error";
    private static final String DEFAULT_SUCCESS_URL="/home";
    private static final String USERNAME_PARAMETER = "username";
    private static final String PASSWORD_PARAMETER="password";

    private static final String[] ENDPOINTS_WHITELIST= {

            REGISTER_URL,
            LOGIN_URL,
            DEFAULT_SUCCESS_URL,
            "/api-docs/**",
            "/verify/email/**",
            "/verified"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeRequests(
                request-> request.antMatchers(ENDPOINTS_WHITELIST).permitAll()
                        .antMatchers( "/totp-login",
                                "/totp-login-error")
                        .hasAuthority(Authority.TOTP_AUTH_AUTHORITY.name())
                        .anyRequest()
                        .hasRole("USER")

        )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and()
                .csrf()
                .disable()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(totpAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class)
                .formLogin(
                        form-> form
                                //.loginPage(LOGIN_URL)
                                .loginProcessingUrl(LOGIN_URL)
//                                .failureUrl(LOGIN_FAIL_URL)
//                                .usernameParameter(USERNAME_PARAMETER)
//                                .passwordParameter(PASSWORD_PARAMETER)
                                .authenticationDetailsSource(new AdditionalAuthenticationDetailsSource())
                                .successHandler(new JwtAuthenticationSuccessHandler(jwtUtils))
                );
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        final AdditionalAuthenticationProvider daoAuthenticationProvider = new AdditionalAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return  daoAuthenticationProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
