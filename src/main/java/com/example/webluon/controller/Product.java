package com.example.webluon.controller;

import com.example.webluon.entity.ProductEntity;
import com.example.webluon.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/product")
public class Product {

    @Autowired
    ProductRepository productRepository;
    @GetMapping("")
    public String index(Model mode){
        List<ProductEntity> listProduct = productRepository.findAll();
        mode.addAttribute("listProduct", listProduct);
        mode.addAttribute("page", "Product");
        return  "Common";
    }
}
