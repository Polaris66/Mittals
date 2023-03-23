package com.example.mittals.Repositories;

import com.example.mittals.Entities.Cart;
import com.example.mittals.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByName(String name);
    List<Product> findAllByCart(Cart cart);
    Product findByName(String name);
    List<Product> findAllByCartIsNull();
    Product findByNameAndCart(String name, Cart cart);
}
