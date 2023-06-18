package com.root.project.portfolio.service;

import com.root.project.portfolio.Portfolio;
import com.root.project.portfolio.repository.PortfolioRepository;
import com.root.project.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements  PortfolioService{
    private  final PortfolioRepository portfolioRepository;


    @Override
    public Portfolio loadPortfolioByUsername(String username){
        return this.portfolioRepository.findPortfolioByUsername(username);
    }

    @Override
    public Portfolio createPorfolio(UserDto userDetail){
        Portfolio portfolio = new Portfolio(userDetail.getUsername(), userDetail.getFirstname(),userDetail.getLastname(),userDetail.getEmail());
        return this.portfolioRepository.save(portfolio);
    }

    @Override
    public Portfolio updatePortfolio(UserDto userDetail){
        Portfolio portfolio = loadPortfolioByUsername(userDetail.getUsername());
        if(portfolio ==null){
            throw new UsernameNotFoundException("invalid portfolio");
        }
        portfolio.setEmail(userDetail.getEmail());
        portfolio.setFirstname(userDetail.getFirstname());
        portfolio.setLastname(userDetail.getLastname());
        portfolio.setUsername(userDetail.getUsername());
        return this.portfolioRepository.save(portfolio);
    }
}
