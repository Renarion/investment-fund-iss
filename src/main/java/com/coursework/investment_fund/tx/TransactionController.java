package com.coursework.investment_fund.tx;

import com.coursework.investment_fund.fund.FundRepository;
import com.coursework.investment_fund.investor.InvestorRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/manager/transactions")
public class TransactionController {

    private final TransactionRepository txRepo;
    private final InvestorRepository investorRepo;
    private final FundRepository fundRepo;

    public TransactionController(TransactionRepository txRepo,
                                 InvestorRepository investorRepo,
                                 FundRepository fundRepo) {
        this.txRepo = txRepo;
        this.investorRepo = investorRepo;
        this.fundRepo = fundRepo;
    }

    @GetMapping
    public String list(
            @RequestParam(required = false) Long investorId,
            @RequestParam(required = false) Long fundId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @RequestParam(defaultValue = "txDate") String sort,
            @RequestParam(defaultValue = "desc") String dir,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        Sort.Direction direction = dir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, 10, Sort.by(direction, sort));

        TransactionType typeEnum = null;
        if (type != null && !type.isBlank()) {
            typeEnum = TransactionType.valueOf(type);
        }

        var txPage = txRepo.search(investorId, fundId, typeEnum, dateFrom, dateTo, pageable);

        model.addAttribute("txPage", txPage);
        model.addAttribute("investors", investorRepo.findAll(Sort.by("fullName")));
        model.addAttribute("funds", fundRepo.findAll(Sort.by("name")));
        model.addAttribute("types", TransactionType.values());

        // текущие фильтры для формы + пагинации
        model.addAttribute("investorId", investorId);
        model.addAttribute("fundId", fundId);
        model.addAttribute("type", type); // строка!
        model.addAttribute("dateFrom", dateFrom);
        model.addAttribute("dateTo", dateTo);
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);

        return "tx/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        TransactionForm form = new TransactionForm();
        form.setTxDate(LocalDate.now());
        form.setType(TransactionType.DEPOSIT);

        model.addAttribute("mode", "create");
        model.addAttribute("form", form);

        model.addAttribute("investors", investorRepo.findAll(Sort.by("fullName")));
        model.addAttribute("funds", fundRepo.findAll(Sort.by("name")));
        model.addAttribute("types", TransactionType.values());

        return "tx/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") TransactionForm form,
                         BindingResult br,
                         Model model) {
        if (br.hasErrors()) {
            model.addAttribute("mode", "create");
            model.addAttribute("investors", investorRepo.findAll(Sort.by("fullName")));
            model.addAttribute("funds", fundRepo.findAll(Sort.by("name")));
            model.addAttribute("types", TransactionType.values());
            return "tx/form";
        }

        var investor = investorRepo.findById(form.getInvestorId()).orElseThrow();
        var fund = fundRepo.findById(form.getFundId()).orElseThrow();

        InvestmentTransaction tx = new InvestmentTransaction();
        tx.setInvestor(investor);
        tx.setFund(fund);
        tx.setType(form.getType());
        tx.setAmount(form.getAmount());
        tx.setTxDate(form.getTxDate());
        tx.setComment(form.getComment());

        txRepo.save(tx);
        return "redirect:/manager/transactions";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        InvestmentTransaction tx = txRepo.findById(id).orElseThrow();

        TransactionForm form = new TransactionForm();
        form.setInvestorId(tx.getInvestor().getId());
        form.setFundId(tx.getFund().getId());
        form.setType(tx.getType());
        form.setAmount(tx.getAmount());
        form.setTxDate(tx.getTxDate());
        form.setComment(tx.getComment());

        model.addAttribute("mode", "edit");
        model.addAttribute("txId", id);
        model.addAttribute("form", form);

        model.addAttribute("investors", investorRepo.findAll(Sort.by("fullName")));
        model.addAttribute("funds", fundRepo.findAll(Sort.by("name")));
        model.addAttribute("types", TransactionType.values());

        return "tx/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("form") TransactionForm form,
                         BindingResult br,
                         Model model) {
        if (br.hasErrors()) {
            model.addAttribute("mode", "edit");
            model.addAttribute("txId", id);
            model.addAttribute("investors", investorRepo.findAll(Sort.by("fullName")));
            model.addAttribute("funds", fundRepo.findAll(Sort.by("name")));
            model.addAttribute("types", TransactionType.values());
            return "tx/form";
        }

        InvestmentTransaction tx = txRepo.findById(id).orElseThrow();
        tx.setInvestor(investorRepo.findById(form.getInvestorId()).orElseThrow());
        tx.setFund(fundRepo.findById(form.getFundId()).orElseThrow());
        tx.setType(form.getType());
        tx.setAmount(form.getAmount());
        tx.setTxDate(form.getTxDate());
        tx.setComment(form.getComment());

        txRepo.save(tx);
        return "redirect:/manager/transactions";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        txRepo.deleteById(id);
        return "redirect:/manager/transactions";
    }
}