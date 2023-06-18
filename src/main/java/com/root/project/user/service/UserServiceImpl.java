package com.root.project.user.service;

import com.root.project.portfolio.service.PortfolioService;
import com.root.project.role.Role;
import com.root.project.role.RoleName;
import com.root.project.role.service.RoleService;
import com.root.project.user.User;
import com.root.project.user.dto.UserDto;
import com.root.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserRepository userRepository;
    private final PortfolioService portfolioService;

    @Override
    @Transactional
    public User createUser(UserDto request){
        User user = User.getInstance()
                .setUsername(request.getUsername())
                .setPassword(encodePassword(request.getPassword()))
                .setAccountNonExpired(request.isAccountNonExpired())
                .setAccountNonLocked(request.isAccountNonLocked())
                .setCredentialsNonExpired(request.isCredentialsNonExpired())
                .setEnabled(request.isEnabled())
                .setEmail(request.getEmail())
                .setFirstname(request.getFirstname())
                .setLastname(request.getLastname())
                .setSecurityPin(encodePassword(request.getSecurityPin()))
                .build();
        Role role = this.roleService.getRoleByName(RoleName.USER);
        user.addRole(role);
        User save = this.userRepository.save(user);
        return save;
    }

    @Override
    public User updateUser(UserDto userDetails){
        Optional<User> user = this.userRepository.findUserByUsername(userDetails.getUsername());
        User newUser = user.map((u) -> {
            u.setUsername(userDetails.getUsername());
            u.setPassword(encodePassword(userDetails.getPassword()));
            u.setAccountNonExpired(userDetails.isAccountNonExpired());
            u.setAccountNonLocked(userDetails.isAccountNonLocked());
            u.setCredentialsNonExpired(userDetails.isCredentialsNonExpired());
            u.setEnabled(userDetails.isEnabled());
            u.setEmail(userDetails.getEmail());
            u.setFirstname(userDetails.getFirstname());
            u.setLastname(userDetails.getLastname());
            u.setSecurityPin(encodePassword(userDetails.getSecurityPin()));
            return u;
        }).orElseThrow(() -> new UsernameNotFoundException("username not foud"));

        User save = this.userRepository.save(newUser);
        if(save!=null){
            this.portfolioService.updatePortfolio(userDetails);
        }
        return save;
    }

    @Override
    public User verfiedUser(String username){
        User enabledUser = this.userRepository.findUserByUsername(username).map(user -> {
            user.setEnabled(true);
            return user;
        }).orElseThrow(() -> new UsernameNotFoundException("username"));
        return this.userRepository.save(enabledUser);
    }

    @Override
    public Optional<User> findUserByUsernameOrEmail(String username){
        return this.userRepository.findUserByUsernameOrEmail(username);
    }

    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }


}
