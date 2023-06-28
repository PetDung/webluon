package com.example.webluon.controller;

import com.example.webluon.entity.ProductEntity;
import com.example.webluon.repositories.ProductRepository;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Location;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Optional;

@Controller
public class Home {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    DatabaseReader databaseReader;
    @GetMapping("/")
    public String index(Model mode, HttpServletRequest request) throws IOException, GeoIp2Exception {
        List<ProductEntity> listProduct = productRepository.findAllByHidden(1);
        String ipAddress = request.getRemoteAddr();
        try {
            InetAddress ip = InetAddress.getByName("1.55.255.255");
            CityResponse response = databaseReader.city(ip);
            String countryIsoCode = response.getCountry().getIsoCode();
            String cityName = response.getCity().getName();
            System.out.println(cityName);
            Location location = response.getLocation();
            System.out.println(location.getLatitude());  // 44.9733
            System.out.println(location.getLongitude());
        }catch (AddressNotFoundException e){
            System.out.println(e);

        }
        mode.addAttribute("listProduct", listProduct);
        mode.addAttribute("page","Home");
        return  "Common";
    }
    @GetMapping("/{id}")
    public String product(@PathVariable Long id,Model mode){
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        mode.addAttribute("product",productEntity.get());
        List<ProductEntity> listProduct = productRepository.findAllByHidden(1);
        mode.addAttribute("listProduct", listProduct);
        mode.addAttribute("page","InfoProduct");
        return  "Common";
    }

}
