package com.root.project.user.events;

import com.root.project.security.SecurityUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserRegisterationEvent extends ApplicationEvent {
    private final SecurityUser securityUser;
    public UserRegisterationEvent(SecurityUser securityUser){
        super(securityUser);
        this.securityUser = securityUser;
    }

}
