package com.example.webluon.controller;
import com.example.webluon.entity.InvoicesEntity;
import com.example.webluon.entity.ProductEntity;
import com.example.webluon.model.OrdersOutput;
import com.example.webluon.repositories.InvoiceItemRepository;
import com.example.webluon.repositories.InvoicesRepository;
import com.example.webluon.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class Admin {
    @Autowired
    ProductRepository productRepository ;
    @Autowired
    InvoicesRepository invoicesRepository;
    @Autowired
    InvoiceItemRepository invoiceItemRepository;


    @GetMapping("")
    public String index(Model model){
        model.addAttribute("page","adminHome");
        return "Admin" ;
    }

    @GetMapping("/postproduct")
    public String postProduct(Model model){
        model.addAttribute("page","PostProduct");
        return "Admin" ;
    }
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("page","Login");
        return "Admin" ;
    }

    @GetMapping("/listproduct")
    public String listProduct(Model model){
        model.addAttribute("page","ProductAdmin");
        List<ProductEntity> listProduct = productRepository.findAllByHidden(1);
        model.addAttribute("listProduct", listProduct);
        return "Admin" ;
    }
    @GetMapping("/listproduct-hidden")
    public String listProductHidden(Model model){
        model.addAttribute("page","ProductAdminHidden");
        List<ProductEntity> listProduct = productRepository.findAllByHidden(0);
        model.addAttribute("listProduct", listProduct);
        return "Admin" ;
    }

    @GetMapping("/listproduct/{id}")
    public String edit(Model model, @PathVariable("id") Long id){
        Optional<ProductEntity> product = productRepository.findById(id);
        ProductEntity productEntity = product.get();
        model.addAttribute("product",productEntity);
        model.addAttribute("page","EditProduct");
        return "Admin";
    }

    @PostMapping("/edit/{id}")
    @ResponseBody
    public String saveEdit(Model model,
                           @PathVariable("id") Long id,
                           @ModelAttribute ProductEntity newProductEntity,
                           @RequestParam("image") MultipartFile image) throws IOException {
        Optional<ProductEntity> product = productRepository.findById(id);
        ProductEntity productEntity = product.get();
        if(!image.isEmpty()){
            byte[] imageData = image.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageData);
            productEntity.setImgBase64(base64Image);
        }
        productEntity.setProductDescription(newProductEntity.getProductDescription());
        productEntity.setName(newProductEntity.getName());
        productEntity.setPrice(newProductEntity.getPrice());
        productEntity.setInventoryCount(newProductEntity.getInventoryCount());

        productRepository.save(productEntity);
        return "ok";
    }

    @PostMapping(path="/post-product")
    @ResponseBody
    public String post(@ModelAttribute ProductEntity productEntity ,
                       @RequestParam("image") MultipartFile image )  throws IOException {
        byte[] imageData = image.getBytes();
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        productEntity.setImgBase64(base64Image);
        productRepository.save(productEntity);
        return "ok";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String deleteProduct(@PathVariable("id") Long id){
        Optional<ProductEntity> product = productRepository.findById(id);
        ProductEntity productEntity = product.get();
        productEntity.setHidden(0);
        productRepository.save(productEntity);
        return "ok";
    }
    @PutMapping ("/hidden/{id}")
    @ResponseBody
    public String hiddenProduct(@PathVariable("id") Long id){
        Optional<ProductEntity> product = productRepository.findById(id);
        ProductEntity productEntity = product.get();
        productEntity.setHidden(1);
        productRepository.save(productEntity);
        return "ok";
    }
    @GetMapping("/pay")
    public String pay(Model model){
        List<OrdersOutput> listOrders = new ArrayList<>();
        List<InvoicesEntity> listInvoice = invoicesRepository.findAllTypeTrue();
        for(InvoicesEntity item : listInvoice){
            List<Object[]> listProduct = productRepository.findOrderedProducts(item.getId());
            OrdersOutput ordersOutput = new OrdersOutput();
            ordersOutput.setInvoice(item);
            ordersOutput.setListProductOutput(listProduct);
            listOrders.add(ordersOutput);
        }
        model.addAttribute("listOrder",listOrders);
        model.addAttribute("page","Pay");
        model.addAttribute("pay","action");
        return "Admin" ;

    }
    @GetMapping("/all-order")
    public String All(Model model){
        List<OrdersOutput> listOrders = new ArrayList<>();
        List<InvoicesEntity> listInvoice = invoicesRepository.findAll();
        for(InvoicesEntity item : listInvoice){
            List<Object[]> listProduct = productRepository.findOrderedProducts(item.getId());
            OrdersOutput ordersOutput = new OrdersOutput();
            ordersOutput.setInvoice(item);
            ordersOutput.setListProductOutput(listProduct);
            listOrders.add(ordersOutput);
        }
        model.addAttribute("listOrder",listOrders);
        model.addAttribute("page","Pay");
        model.addAttribute("all","action");
        return "Admin" ;

    }
    @GetMapping("/unpay")
    public String unPay(Model model){
        List<OrdersOutput> listOrders = new ArrayList<>();
        List<InvoicesEntity> listInvoice = invoicesRepository.findAllByType(0);
        for(InvoicesEntity item : listInvoice){
            List<Object[]> listProduct = productRepository.findOrderedProducts(item.getId());
            OrdersOutput ordersOutput = new OrdersOutput();
            ordersOutput.setInvoice(item);
            ordersOutput.setListProductOutput(listProduct);
            listOrders.add(ordersOutput);
        }
        model.addAttribute("listOrder",listOrders);
        model.addAttribute("page","Pay");
        model.addAttribute("unpay","action");
        return "Admin" ;

    }
    @GetMapping("/ship")
    public String ship(Model model){
        List<OrdersOutput> listOrders = new ArrayList<>();
        List<InvoicesEntity> listInvoice = invoicesRepository.findAllByIsShip(1);
        for(InvoicesEntity item : listInvoice){
            List<Object[]> listProduct = productRepository.findOrderedProducts(item.getId());
            OrdersOutput ordersOutput = new OrdersOutput();
            ordersOutput.setInvoice(item);
            ordersOutput.setListProductOutput(listProduct);
            listOrders.add(ordersOutput);
        }
        model.addAttribute("listOrder",listOrders);
        model.addAttribute("page","Pay");
        model.addAttribute("ship","action");
        return "Admin" ;

    }
    @PutMapping("/pay/{id}")
    @ResponseBody
    public String isPay(@PathVariable("id") Long id){
        Optional<InvoicesEntity> invoicesOpp = invoicesRepository.findById(id);
        InvoicesEntity invoicesEntity = invoicesOpp.get();
        int type = invoicesEntity.getType();
        if(type==1) invoicesEntity.setType(0);
        else invoicesEntity.setType(1);
        invoicesRepository.save(invoicesEntity);
        return "oke";
    }
    @PutMapping("/ship/{id}")
    @ResponseBody
    public String isShip(@PathVariable("id") Long id){
        Optional<InvoicesEntity> invoicesOpp = invoicesRepository.findById(id);
        InvoicesEntity invoicesEntity = invoicesOpp.get();
        invoicesEntity.setType(1);
        invoicesEntity.setIsShip(1);
        invoicesRepository.save(invoicesEntity);
        return "oke";
    }
}
