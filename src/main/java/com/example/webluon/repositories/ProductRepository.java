package com.example.webluon.repositories;

import com.example.webluon.entity.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
   List<ProductEntity> findAll();

   @Override
   Optional<ProductEntity> findById(Long id);

}
