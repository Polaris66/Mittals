package com.example.mittals.Entities;


import jakarta.persistence.*;

import java.util.Set;

@Entity(name="carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="customerId")
    private Long customerId;



    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @OneToMany(mappedBy="cart")
    private Set<Product> products;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
