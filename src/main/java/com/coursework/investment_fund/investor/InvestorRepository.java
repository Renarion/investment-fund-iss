package com.coursework.investment_fund.investor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestorRepository extends JpaRepository<Investor, Long> {
    Page<Investor> findByFullNameContainingIgnoreCase(String q, Pageable pageable);
}