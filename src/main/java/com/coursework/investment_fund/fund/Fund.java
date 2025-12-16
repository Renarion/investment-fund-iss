package com.coursework.investment_fund.fund;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "funds")
public class Fund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String strategy; // например: "Консервативная", "Сбалансированная"

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String riskLevel; // Low/Medium/High

    @NotBlank
    @Size(max = 10)
    @Column(nullable = false, length = 10)
    private String currency; // RUB, USD, EUR

    @NotNull
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal nav; // стоимость пая/условная NAV

    @NotNull
    @Column(nullable = false)
    private LocalDate createdDate;

    public Fund() {}

    // getters/setters
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStrategy() { return strategy; }
    public void setStrategy(String strategy) { this.strategy = strategy; }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public BigDecimal getNav() { return nav; }
    public void setNav(BigDecimal nav) { this.nav = nav; }

    public LocalDate getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }
}