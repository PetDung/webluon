package com.example.webluon.repositories;

import com.example.webluon.entity.InvoicesEntity;
import com.example.webluon.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoicesRepository extends JpaRepository<InvoicesEntity,Long> {
    Optional<InvoicesEntity> findByNumberPhone (String numberPhone);
    List<InvoicesEntity> findAll ();
    List<InvoicesEntity> findAllByType (int type);
    @Query("  SELECT i " +
            " FROM InvoicesEntity i " +
            " WHERE i.isShip = 0 and i.type = 1 ")
    List<InvoicesEntity> findAllTypeTrue();

    List<InvoicesEntity> findAllByIsShip(int isShip);

}
