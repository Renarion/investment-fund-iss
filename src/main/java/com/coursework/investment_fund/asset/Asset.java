package com.coursework.investment_fund.asset;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "assets")
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 15)
    @Column(nullable = false, unique = true, length = 15)
    private String ticker; // AAPL, SBER, FXUS

    @NotBlank
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String name;

    @NotBlank
    @Size(max = 20)
    @Column(nullable = false, length = 20)
    private String type; // Stock, Bond, ETF, Crypto, Other

    @Size(max = 50)
    @Column(length = 50)
    private String sector; // Tech, Finance...

    @NotBlank
    @Size(max = 10)
    @Column(nullable = false, length = 10)
    private String currency; // RUB, USD, EUR

    @NotNull
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal price;

    public Asset() {}

    // getters/setters
    public Long getId() { return id; }

    public String getTicker() { return ticker; }
    public void setTicker(String ticker) { this.ticker = ticker; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
}