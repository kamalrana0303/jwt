package com.root.project.portfolio;

import com.root.project.BaseId;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Portfolio extends BaseId {
    @Column(unique = true)
    private String username;
    private String firstname;
    private String lastname;
    private String email;

}
