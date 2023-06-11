package com.example.webluon.controller;

import com.example.webluon.entity.ProductEntity;
import com.example.webluon.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class Admin {
    @GetMapping("")
    public String index(){
        return "Admin" ;
    }
    @Autowired
    ProductRepository productRepository ;
    @PostMapping(path="/post-product", consumes = "application/x-www-form-urlencoded")
    public String post(ProductEntity productEntity){
        productRepository.save(productEntity);
        return "Admin";
    }
}
