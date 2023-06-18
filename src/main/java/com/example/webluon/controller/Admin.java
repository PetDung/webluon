package com.example.webluon.controller;
import com.example.webluon.entity.InvoiceItemEntity;
import com.example.webluon.entity.InvoicesEntity;
import com.example.webluon.entity.ProductEntity;
import com.example.webluon.model.OrdersOutput;
import com.example.webluon.model.ProductOutput;
import com.example.webluon.repositories.InvoiceItemRepository;
import com.example.webluon.repositories.InvoicesRepository;
import com.example.webluon.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    InvoicesRepository invoicesRepository;
    @Autowired
    InvoiceItemRepository invoiceItemRepository;
    @GetMapping("/order")
    public String order(Model model){
        List<OrdersOutput> listOrders = new ArrayList<>();
        List<InvoicesEntity> listInvoice = invoicesRepository.findAll();
        for(InvoicesEntity item : listInvoice){
            List<Object[]> listProduct = productRepository.findOrderedProducts(item.getId());
            OrdersOutput ordersOutput = new OrdersOutput();
            ordersOutput.setInvoice(item);
            ordersOutput.setListProductOutput(listProduct);
            listOrders.add(ordersOutput);
        }
        model.addAttribute("listOder",listOrders);
        return "Order" ;

    }
}
