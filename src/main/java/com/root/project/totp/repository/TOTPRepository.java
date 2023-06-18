package com.root.project.totp.repository;

import com.root.project.totp.TOTPDetails;
import org.springframework.data.repository.CrudRepository;

public interface TOTPRepository extends CrudRepository<TOTPDetails,Long> {
    void deleteByUsername(String username);

    TOTPDetails findByUsername(String username);
}
