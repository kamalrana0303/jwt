package com.root.project.security;

import com.root.project.role.Authority;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@RequiredArgsConstructor
public class JwtAuthenticationSuccessHandler  implements AuthenticationSuccessHandler {
    private final JwtUtils jwtUtils;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(requiresTotpAuthentication(authentication)){
            redirectStrategy.sendRedirect(request,response,"/totp-login");
        }
        else{
            UserDetails userDetails = (UserDetails)authentication.getPrincipal();
            String token = jwtUtils.generateToken(userDetails);
            response.addHeader("Authorization","BEARER "+ token);
            redirectStrategy.sendRedirect(request,response,"/portfolio");
        }

    }
    private boolean requiresTotpAuthentication(Authentication authentication){
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        return authorities.contains(Authority.TOTP_AUTH_AUTHORITY.name());
    }
}
