package com.root.project.verification;

import com.root.project.BaseId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Verification extends BaseId {
    @Column(unique = true,nullable = false)
    private String verificationId;
    @Column(unique = true,nullable = false)
    private String username;
    private Date date;
}
