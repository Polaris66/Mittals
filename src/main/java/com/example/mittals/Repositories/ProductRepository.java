package com.example.mittals.Repositories;

import com.example.mittals.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
