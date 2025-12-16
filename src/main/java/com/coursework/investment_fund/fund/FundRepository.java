package com.coursework.investment_fund.fund;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundRepository extends JpaRepository<Fund, Long> {
    Page<Fund> findByNameContainingIgnoreCase(String q, Pageable pageable);
}