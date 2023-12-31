package com.example.webluon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity extends BaseEntity {

    @Column
    private String name;
    @Column
    private double price;
    @Column(columnDefinition = "TEXT")
    private String productDescription;
    @Column
    private int inventoryCount;
    @Column
    private int hidden = 1;

    @Lob
    @Column(length=1048576)
    private String imgBase64;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvoiceItemEntity> listInvoice = new ArrayList<>();

}
