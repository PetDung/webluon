package com.example.webluon.model;

import com.example.webluon.entity.InvoicesEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersOutput {
    private InvoicesEntity invoice;
    private List<Object[]> listProductOutput = new ArrayList <> ();
}
