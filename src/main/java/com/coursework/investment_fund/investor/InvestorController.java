package com.coursework.investment_fund.investor;

import org.springframework.dao.DataIntegrityViolationException;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/manager/investors")
public class InvestorController {

    private final InvestorRepository repo;

    public InvestorController(InvestorRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "fullName") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        Sort.Direction d = dir.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, 10, Sort.by(d, sort));

        Page<Investor> investors = q.isBlank()
                ? repo.findAll(pageable)
                : repo.findByFullNameContainingIgnoreCase(q, pageable);

        model.addAttribute("investors", investors);
        model.addAttribute("q", q);
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        return "investors/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("investor", new Investor());
        model.addAttribute("mode", "create");
        return "investors/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Investor investor, BindingResult br, Model model) {
    if (br.hasErrors()) {
        model.addAttribute("mode", "create");
        return "investors/form";
    }
    try {
        repo.save(investor);
    } catch (DataIntegrityViolationException ex) {
        model.addAttribute("mode", "create");
        model.addAttribute("dbError", "Инвестор с таким email уже существует");
        return "investors/form";
    }
    return "redirect:/manager/investors";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("investor", repo.findById(id).orElseThrow());
        model.addAttribute("mode", "edit");
        return "investors/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Investor investor, BindingResult br, Model model) {
    if (br.hasErrors()) {
        model.addAttribute("mode", "edit");
        return "investors/form";
    }
    Investor existing = repo.findById(id).orElseThrow();
    existing.setFullName(investor.getFullName());
    existing.setEmail(investor.getEmail());
    existing.setPhone(investor.getPhone());

    try {
        repo.save(existing);
    } catch (DataIntegrityViolationException ex) {
        model.addAttribute("mode", "edit");
        model.addAttribute("dbError", "Инвестор с таким email уже существует");
        model.addAttribute("investor", existing);
        return "investors/form";
    }
    return "redirect:/manager/investors";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/manager/investors";
    }
}