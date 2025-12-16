package com.coursework.investment_fund;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("appName", "Информационно-справочная система инвестиционного фонда");
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}