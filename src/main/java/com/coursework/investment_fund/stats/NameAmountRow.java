package com.coursework.investment_fund.stats;

import java.math.BigDecimal;

public class NameAmountRow {
    private final String name;
    private final BigDecimal amount;

    public NameAmountRow(String name, BigDecimal amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() { return name; }
    public BigDecimal getAmount() { return amount; }
}