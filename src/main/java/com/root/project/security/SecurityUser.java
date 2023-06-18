package com.root.project.security;

import com.root.project.role.Authority;
import com.root.project.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SecurityUser implements UserDetails {
    Collection<GrantedAuthority> authorities =null;
    private final User user;

    public SecurityUser(User user) {
        Stream<SimpleGrantedAuthority> authoritiesStream = user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRoleName()));
        if(user.isTotpEnabled()){
            authoritiesStream = Stream.concat(authoritiesStream, Stream.of(new SimpleGrantedAuthority(Authority.TOTP_AUTH_AUTHORITY.name())));
        }
        this.authorities = authoritiesStream.collect(Collectors.toUnmodifiableList());
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public String getEmail(){
        return user.getEmail();
    }

    public String getSecurityPin(){
        return user.getSecurityPin();
    }

}
