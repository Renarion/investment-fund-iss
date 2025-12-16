package com.coursework.investment_fund.tx;

import com.coursework.investment_fund.fund.Fund;
import com.coursework.investment_fund.investor.Investor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class InvestmentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "investor_id")
    private Investor investor;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "fund_id")
    private Fund fund;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TransactionType type;

    @NotNull
    @Positive
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @NotNull
    @Column(nullable = false)
    private LocalDate txDate;

    @Column(length = 255)
    private String comment;

    public InvestmentTransaction() {}

    public Long getId() { return id; }

    public Investor getInvestor() { return investor; }
    public void setInvestor(Investor investor) { this.investor = investor; }

    public Fund getFund() { return fund; }
    public void setFund(Fund fund) { this.fund = fund; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getTxDate() { return txDate; }
    public void setTxDate(LocalDate txDate) { this.txDate = txDate; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}