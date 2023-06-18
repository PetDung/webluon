package com.example.webluon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class Cart {
    @GetMapping("")
    public String index(Model model){
        model.addAttribute("page","Cart");
        return "Common" ;
    }
}
