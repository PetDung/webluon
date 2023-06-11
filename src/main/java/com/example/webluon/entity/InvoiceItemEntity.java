package com.example.webluon.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="invoice_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceItemEntity extends BaseEntity{

    @ManyToOne
    @JoinColumn(name ="product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name ="orders_id")
    private InvoicesEntity invoices;

    @Column
    private  int quanty;

    @Column
    private int isPay;

}
