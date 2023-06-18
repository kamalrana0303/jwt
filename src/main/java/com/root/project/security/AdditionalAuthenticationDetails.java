package com.root.project.security;

import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

public class AdditionalAuthenticationDetails  extends WebAuthenticationDetails {

    @Getter
    private String securityPin;

    public AdditionalAuthenticationDetails(HttpServletRequest request) {
        super(request);
        String securityPin = request.getParameter("securityPin");
        if(securityPin !=null){
            this.securityPin = securityPin;
        }

    }
}
