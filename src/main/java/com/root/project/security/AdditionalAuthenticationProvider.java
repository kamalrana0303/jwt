package com.root.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


public class AdditionalAuthenticationProvider  extends DaoAuthenticationProvider {
    AdditionalAuthenticationProvider(){
        super();
    }
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException{
        super.additionalAuthenticationChecks(userDetails,authentication);
        AdditionalAuthenticationDetails details =(AdditionalAuthenticationDetails) authentication.getDetails();
        SecurityUser user = (SecurityUser) userDetails;
        if(!getPasswordEncoder().matches(details.getSecurityPin(),user.getSecurityPin())){
            throw new BadCredentialsException("invalid security pin");
        }
    }

    @Override
    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService){
        super.setUserDetailsService(userDetailsService);
    }

    @Override
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder){
        super.setPasswordEncoder(passwordEncoder);
    }
}
