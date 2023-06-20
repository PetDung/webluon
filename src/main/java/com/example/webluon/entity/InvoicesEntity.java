package com.example.webluon.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "invoices")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoicesEntity extends BaseEntity {

    @Column
    private String nameCustom;
    @Column
    private String email;
    @Column
    private String numberPhone;

    @Column
    private  String address;

    @Column
    private Double sumPrice;

    @Column
    private int type=0;

    @Column
    private int typePay;


    @Column(columnDefinition = "TEXT")
    private String note;

    @OneToMany(mappedBy = "invoices", fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<InvoiceItemEntity> listInvoice = new ArrayList<>();


}
