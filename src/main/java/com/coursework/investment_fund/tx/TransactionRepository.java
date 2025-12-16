package com.coursework.investment_fund.tx;

import com.coursework.investment_fund.stats.NameAmountRow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<InvestmentTransaction, Long> {

    // =========================
    // 1. Поиск для журнала операций
    // =========================
    @Query("""
        select t from InvestmentTransaction t
        where (:investorId is null or t.investor.id = :investorId)
          and (:fundId is null or t.fund.id = :fundId)
          and (:type is null or t.type = :type)
          and (:dateFrom is null or t.txDate >= :dateFrom)
          and (:dateTo is null or t.txDate <= :dateTo)
        """)
    Page<InvestmentTransaction> search(
            @Param("investorId") Long investorId,
            @Param("fundId") Long fundId,
            @Param("type") TransactionType type,
            @Param("dateFrom") LocalDate dateFrom,
            @Param("dateTo") LocalDate dateTo,
            Pageable pageable
    );

    boolean existsByFundId(Long fundId);
    boolean existsByInvestorId(Long investorId);

    // =========================
    // 2. Агрегаты для статистики
    // =========================

    /** Количество уникальных инвесторов */
    @Query("select count(distinct t.investor.id) from InvestmentTransaction t")
    long countDistinctInvestors();

    /** Общее количество операций */
    @Query("select count(t) from InvestmentTransaction t")
    long countTransactions();

    /** Общая сумма вложений (DEPOSIT - WITHDRAW) */
    @Query("""
        select coalesce(sum(
          case
            when t.type = com.coursework.investment_fund.tx.TransactionType.DEPOSIT then t.amount
            when t.type = com.coursework.investment_fund.tx.TransactionType.WITHDRAW then -t.amount
            else 0
          end
        ), 0)
        from InvestmentTransaction t
    """)
    BigDecimal totalNetInflow();

    /** Топ фондов по сумме вложений */
    @Query("""
        select new com.coursework.investment_fund.stats.NameAmountRow(
          t.fund.name,
          coalesce(sum(
            case
              when t.type = com.coursework.investment_fund.tx.TransactionType.DEPOSIT then t.amount
              when t.type = com.coursework.investment_fund.tx.TransactionType.WITHDRAW then -t.amount
              else 0
            end
          ), 0)
        )
        from InvestmentTransaction t
        group by t.fund.id, t.fund.name
        order by coalesce(sum(
            case
              when t.type = com.coursework.investment_fund.tx.TransactionType.DEPOSIT then t.amount
              when t.type = com.coursework.investment_fund.tx.TransactionType.WITHDRAW then -t.amount
              else 0
            end
        ), 0) desc
    """)
    List<NameAmountRow> topFunds(Pageable pageable);

    /** Топ инвесторов по сумме вложений */
    @Query("""
        select new com.coursework.investment_fund.stats.NameAmountRow(
          t.investor.fullName,
          coalesce(sum(
            case
              when t.type = com.coursework.investment_fund.tx.TransactionType.DEPOSIT then t.amount
              when t.type = com.coursework.investment_fund.tx.TransactionType.WITHDRAW then -t.amount
              else 0
            end
          ), 0)
        )
        from InvestmentTransaction t
        group by t.investor.id, t.investor.fullName
        order by coalesce(sum(
            case
              when t.type = com.coursework.investment_fund.tx.TransactionType.DEPOSIT then t.amount
              when t.type = com.coursework.investment_fund.tx.TransactionType.WITHDRAW then -t.amount
              else 0
            end
        ), 0) desc
    """)
    List<NameAmountRow> topInvestors(Pageable pageable);
}