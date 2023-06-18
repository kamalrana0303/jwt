package com.root.project.verification.repository;

import com.root.project.verification.Verification;
import org.springframework.data.repository.CrudRepository;

public interface VerificationRepsitory extends CrudRepository<Verification,Long> {

    Verification findByUsername(String username);

    Verification findByVerificationId(String id);
}
