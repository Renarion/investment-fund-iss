package com.coursework.investment_fund.fund;

import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/manager/funds")
public class FundController {

    private final FundRepository repo;

    public FundController(FundRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        Sort.Direction direction = dir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, 10, Sort.by(direction, sort));

        Page<Fund> funds = q.isBlank()
                ? repo.findAll(pageable)
                : repo.findByNameContainingIgnoreCase(q, pageable);

        model.addAttribute("funds", funds);
        model.addAttribute("q", q);
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        return "funds/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        Fund f = new Fund();
        f.setCreatedDate(LocalDate.now());
        model.addAttribute("fund", f);
        model.addAttribute("mode", "create");
        return "funds/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("fund") Fund fund, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("mode", "create");
            return "funds/form";
        }
        repo.save(fund);
        return "redirect:/manager/funds";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Fund fund = repo.findById(id).orElseThrow();
        model.addAttribute("fund", fund);
        model.addAttribute("mode", "edit");
        return "funds/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("fund") Fund fund, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("mode", "edit");
            return "funds/form";
        }
        Fund existing = repo.findById(id).orElseThrow();
        existing.setName(fund.getName());
        existing.setStrategy(fund.getStrategy());
        existing.setRiskLevel(fund.getRiskLevel());
        existing.setCurrency(fund.getCurrency());
        existing.setNav(fund.getNav());
        existing.setCreatedDate(fund.getCreatedDate());
        repo.save(existing);
        return "redirect:/manager/funds";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/manager/funds";
    }
}