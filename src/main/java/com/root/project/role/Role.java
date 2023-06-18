package com.root.project.role;

import com.root.project.BaseId;
import com.root.project.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseId {
    @Column(unique = true)
    private String roleName;
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name="user_id")})
    private Set<User> users = new HashSet<>();
}
