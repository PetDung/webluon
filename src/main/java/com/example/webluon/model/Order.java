package com.example.webluon.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private String name;
    private String email;
    private String numberPhone;
    private String address;
    private int type;
    private List<Product> listProduct;
    private List<Integer> quanty;
    private Double sumPrice;
}
