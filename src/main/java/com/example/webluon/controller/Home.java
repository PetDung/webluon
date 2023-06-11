package com.example.webluon.controller;

import com.example.webluon.entity.ProductEntity;
import com.example.webluon.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
public class Home {

    @Autowired
    ProductRepository productRepository;
    @GetMapping("/")
    public String index(Model mode){
        List<ProductEntity> listProduct = productRepository.findAll();
        mode.addAttribute("listProduct", listProduct);
        return  "Home";
    }
    @GetMapping("/{id}")
    public String product(@PathVariable Long id,Model mode){
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        mode.addAttribute("product",productEntity.get());
        List<ProductEntity> listProduct = productRepository.findAll();
        mode.addAttribute("listProduct", listProduct);
        return "InfoProduct" ;
    }
}
