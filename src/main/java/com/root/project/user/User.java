package com.root.project.user;


import com.root.project.BaseId;
import com.root.project.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User extends BaseId {
    private String password;
    @Column(name="username",unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8_bin")
    private String username;
    @Column(name="email", unique = true, columnDefinition="VARCHAR(255) COLLATE utf8_bin")
    private String email;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private String securityPin;
    private boolean isTotpEnabled;
    @ManyToMany(mappedBy = "users",fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role){
        role.getUsers().add(this);
        roles.add(role);
    }

    public static class Builder{
        private String password;
        private String username;
        private boolean accountNonExpired;
        private boolean accountNonLocked;
        private boolean credentialsNonExpired;
        private boolean enabled;
        private String email;
        private String firstName;
        private String lastName;
        private String securityPin;

        public Builder(){}

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }
        public Builder setFirstname(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public Builder setLastname(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setAccountNonExpired(boolean accountNonExpired) {
            this.accountNonExpired = accountNonExpired;
            return this;
        }

        public Builder setAccountNonLocked(boolean accountNonLocked) {
            this.accountNonLocked = accountNonLocked;
            return this;
        }

        public Builder setCredentialsNonExpired(boolean credentialsNonExpired) {
            this.credentialsNonExpired = credentialsNonExpired;
            return this;
        }



        public Builder setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }
        public Builder setSecurityPin(String securityPin) {
            this.securityPin = securityPin;
            return this;
        }

        public User build(){
            User user = new User();
            user.setCredentialsNonExpired(credentialsNonExpired);
            user.setAccountNonExpired(accountNonExpired);
            user.setAccountNonLocked(accountNonLocked);
            user.setEnabled(this.enabled);
            user.setPassword(password);
            user.setUsername(username);
            user.setEmail(email);
            user.setFirstname(firstName);
            user.setLastname(lastName);
            user.setSecurityPin(securityPin);
            return user;
        }
    }

    public static Builder getInstance(){
        return new User.Builder();
    }

}
