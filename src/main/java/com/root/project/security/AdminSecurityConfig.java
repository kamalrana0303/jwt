package com.root.project.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

@RequiredArgsConstructor
@Order(1)
@EnableWebSecurity
public class AdminSecurityConfig {
    private final UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain  adminFilterChain(HttpSecurity http) throws Exception{
        http.antMatcher("/support/admin/**")
                .addFilter(getDigestAuthFilter())
                .exceptionHandling()
                .authenticationEntryPoint(getDigestEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/support/admin/**")
                .hasRole("ADMIN");
        return http.build();
    }
    public DigestAuthenticationFilter getDigestAuthFilter(){
        DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
        digestAuthenticationFilter.setUserDetailsService(userDetailsService);
        digestAuthenticationFilter.setAuthenticationEntryPoint(getDigestEntryPoint());
        digestAuthenticationFilter.setPasswordAlreadyEncoded(true);
        digestAuthenticationFilter.setCreateAuthenticatedToken(true);
        return digestAuthenticationFilter;
    }
    private DigestAuthenticationEntryPoint getDigestEntryPoint(){
        DigestAuthenticationEntryPoint entryPoint = new DigestAuthenticationEntryPoint();
        entryPoint.setRealmName("admin-digest-realm");
        entryPoint.setKey("sfjlskfjklsf");
        return entryPoint;
    }
}
