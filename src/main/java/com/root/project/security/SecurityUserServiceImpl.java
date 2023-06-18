package com.root.project.security;

import com.root.project.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public SecurityUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findUserByUsernameOrEmail(username).map(u->{
            SecurityUser securityUser = new SecurityUser(u);
            return securityUser;
        }).orElseThrow(()-> {
            return new UsernameNotFoundException("username/email not found");
        });
    }
}
