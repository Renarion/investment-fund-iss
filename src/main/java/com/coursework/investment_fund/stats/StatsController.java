package com.coursework.investment_fund.stats;

import com.coursework.investment_fund.tx.TransactionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@Controller
public class StatsController {

    private final TransactionRepository txRepo;

    public StatsController(TransactionRepository txRepo) {
        this.txRepo = txRepo;
    }

    @GetMapping("/manager/stats")
    public String stats(Model model) {
        long investors = txRepo.countDistinctInvestors();
        long operations = txRepo.countTransactions();
        BigDecimal totalNet = txRepo.totalNetInflow();

        var topFunds = txRepo.topFunds(PageRequest.of(0, 5));
        var topInvestors = txRepo.topInvestors(PageRequest.of(0, 5));

        model.addAttribute("investors", investors);
        model.addAttribute("operations", operations);
        model.addAttribute("totalNet", totalNet);

        model.addAttribute("topFunds", topFunds);
        model.addAttribute("topInvestors", topInvestors);

        return "stats/index";
    }
}