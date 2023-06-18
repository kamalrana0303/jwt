package com.root.project.portfolio.repository;

import com.root.project.portfolio.Portfolio;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PortfolioRepository extends CrudRepository<Portfolio, Long> {
    Portfolio findPortfolioByUsername(String username);
}
