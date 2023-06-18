package com.root.project.portfolio.service;

import com.root.project.portfolio.Portfolio;
import com.root.project.user.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface PortfolioService {
    Portfolio loadPortfolioByUsername(String username);

    Portfolio createPorfolio(UserDto userDetail);

    Portfolio updatePortfolio(UserDto userDetail);
}
