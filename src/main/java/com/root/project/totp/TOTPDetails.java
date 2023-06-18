package com.root.project.totp;

import com.root.project.BaseId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@RequiredArgsConstructor
@Entity
public class TOTPDetails extends BaseId {
    @Column(unique = true)
    private final String username;
    private final String secret;

}
