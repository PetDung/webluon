package com.example.webluon.controller;

import com.example.webluon.entity.InvoiceItemEntity;
import com.example.webluon.entity.InvoicesEntity;
import com.example.webluon.entity.ProductEntity;
import com.example.webluon.model.Order;
import com.example.webluon.model.Product;
import com.example.webluon.repositories.InvoiceItemRepository;
import com.example.webluon.repositories.InvoicesRepository;
import com.example.webluon.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/checkout")
public class Checkout {
    @Autowired
    InvoicesRepository invoicesRepository;

    @Autowired
    InvoiceItemRepository invoiceItemRepository;

    @Autowired
    ProductRepository productRepository;

    @GetMapping
    public String index(){
        return"Checkout" ;
    }

    @PostMapping(path="/order", consumes = "application/json")
    public String  order(@RequestBody Order order){

        Optional<InvoicesEntity> isInvoices = invoicesRepository.findByNumberPhone(order.getNumberPhone());
        InvoicesEntity invoices;
        if(isInvoices.isEmpty()){
            invoices = new InvoicesEntity();
            invoices.setNumberPhone(order.getNumberPhone());
        }else{
            invoices = isInvoices.get();
        }
        invoices.setNameCustom(order.getName());
        invoices.setEmail(order.getEmail());
        invoices.setType(order.getType());
        invoices.setSumPrice(order.getSumPrice());
        invoicesRepository.save(invoices);

        List<ProductEntity> listProductEntity = new  ArrayList <> ();
        for(Product product : order.getListProduct()){
            Long id = product.getId();
            ProductEntity productEntity = productRepository.findById(id).get();
            InvoiceItemEntity invoiceItemEntity = new InvoiceItemEntity();
            invoiceItemEntity.setInvoices(invoices);
            invoiceItemEntity.setQuanty(order.getQuanty().get(Math.toIntExact(id)));
            invoiceItemEntity.setProduct(productEntity);
            invoiceItemEntity.setIsPay(0);
            invoiceItemRepository.save(invoiceItemEntity);
        }
//        List<ProductEntity> l = invoicesRepository.findAllProductByInvoiceId(3L);
//        System.out.println(l.toString());
        return "Home";
    }
}

