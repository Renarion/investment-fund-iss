package com.coursework.investment_fund.asset;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    Page<Asset> findByTickerContainingIgnoreCaseOrNameContainingIgnoreCase(String t, String n, Pageable pageable);
}