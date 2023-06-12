package com.example.webluon.repositories;

import com.example.webluon.entity.InvoiceItemEntity;
import com.example.webluon.entity.InvoicesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItemEntity,Long> {
    List<InvoiceItemEntity> findAllByInvoices (InvoicesEntity invoices );
}
