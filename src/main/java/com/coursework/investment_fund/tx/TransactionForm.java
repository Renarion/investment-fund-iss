package com.coursework.investment_fund.tx;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionForm {

    @NotNull
    private Long investorId;

    @NotNull
    private Long fundId;

    @NotNull
    private TransactionType type;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private LocalDate txDate;

    private String comment;

    public Long getInvestorId() { return investorId; }
    public void setInvestorId(Long investorId) { this.investorId = investorId; }

    public Long getFundId() { return fundId; }
    public void setFundId(Long fundId) { this.fundId = fundId; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getTxDate() { return txDate; }
    public void setTxDate(LocalDate txDate) { this.txDate = txDate; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}