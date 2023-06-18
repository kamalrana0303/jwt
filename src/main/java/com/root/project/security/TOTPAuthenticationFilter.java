package com.root.project.security;

import com.root.project.exception.InvalidTOTPVerificationCode;
import com.root.project.role.Authority;
import com.root.project.totp.service.TOTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TOTPAuthenticationFilter extends GenericFilterBean {
    private final TOTPService totpService;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private String onSuccessUrl = "/portfolio";
    private String onfailureUrl = "totp-login-error";


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.createEmptyContext().getAuthentication();
        String code = obtainCode(servletRequest);
        if(code == null || !requiresTotpAuthentication(authentication)){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        if(codeIsValid(authentication.getName(),Integer.valueOf(code))){
            Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            authorities.remove(Authority.TOTP_AUTH_AUTHORITY.name());
            authentication = new UsernamePasswordAuthenticationToken(authentication.getName(),authentication.getCredentials(),buildAuthorities(authorities));
            SecurityContextHolder.getContext().setAuthentication(authentication);
           // redirectStrategy.sendRedirect((HttpServletRequest) servletRequest,(HttpServletResponse) servletResponse  ,onSuccessUrl );
        }else{
            throw new InvalidTOTPVerificationCode("invalid verification code");
        }
    }

    private Collection<? extends GrantedAuthority> buildAuthorities(Set<String> authorities) {
       return authorities.stream().map(a-> new SimpleGrantedAuthority(a)).collect(Collectors.toUnmodifiableList());
    }

    private boolean codeIsValid(String name, Integer code) {
        return code !=null && totpService.verifyCode(name, code);
    }

    private boolean requiresTotpAuthentication(Authentication authentication) {
        if(authentication == null){
            return false;
        }
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        boolean hasTotpAuthority =  authorities.contains(Authority.TOTP_AUTH_AUTHORITY.name());
        return hasTotpAuthority && authentication.isAuthenticated();
    }

    private String obtainCode(ServletRequest servletRequest) {
        return servletRequest.getParameter("totp_code");
    }
}
