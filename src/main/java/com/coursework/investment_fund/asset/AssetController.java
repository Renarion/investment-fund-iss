package com.coursework.investment_fund.asset;

import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager/assets")
public class AssetController {

    private final AssetRepository repo;

    public AssetController(AssetRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String list(
            @RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "ticker") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            @RequestParam(defaultValue = "0") int page,
            Model model
    ) {
        Sort.Direction direction = dir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, 10, Sort.by(direction, sort));

        Page<Asset> assets = q.isBlank()
                ? repo.findAll(pageable)
                : repo.findByTickerContainingIgnoreCaseOrNameContainingIgnoreCase(q, q, pageable);

        model.addAttribute("assets", assets);
        model.addAttribute("q", q);
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);
        return "assets/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("asset", new Asset());
        model.addAttribute("mode", "create");
        return "assets/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("asset") Asset asset, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("mode", "create");
            return "assets/form";
        }
        repo.save(asset);
        return "redirect:/manager/assets";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Asset asset = repo.findById(id).orElseThrow();
        model.addAttribute("asset", asset);
        model.addAttribute("mode", "edit");
        return "assets/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("asset") Asset asset, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("mode", "edit");
            return "assets/form";
        }
        Asset existing = repo.findById(id).orElseThrow();
        existing.setTicker(asset.getTicker());
        existing.setName(asset.getName());
        existing.setType(asset.getType());
        existing.setSector(asset.getSector());
        existing.setCurrency(asset.getCurrency());
        existing.setPrice(asset.getPrice());
        repo.save(existing);
        return "redirect:/manager/assets";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/manager/assets";
    }
}