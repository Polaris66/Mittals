package com.example.mittals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.mittals.Entities.Cart;
import com.example.mittals.Entities.Product;
import com.example.mittals.Entities.User;
import com.example.mittals.Repositories.CartRepository;
import com.example.mittals.Repositories.ProductRepository;
import com.example.mittals.Repositories.UserRepository;

@Component
public class AppStartupRunner implements ApplicationRunner{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;
    
    @Override
    public void run(ApplicationArguments args) throws Exception{
        User admin = userRepository.findByEmail("admin@gmail.com");
        if(admin==null){
            admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setPassword("admin");
            admin.setRole(2);
            userRepository.save(admin);
        }

        User manager = userRepository.findByEmail("manager@gmail.com");
        if(manager==null){
            manager = new User();
            manager.setEmail("manager@gmail.com");
            manager.setPassword("manager");
            manager.setRole(1);
            userRepository.save(manager);
        }

        User customer = userRepository.findByEmail("customer@gmail.com");
        if(customer==null){
            customer = new User();
            customer.setEmail("customer@gmail.com");
            customer.setPassword("customer");
            customer.setRole(0);
            customer.setWallet(1000);
            Cart cart = new Cart();
            cartRepository.save(cart);
            customer.setCart(cart);
            userRepository.save(customer);
        }

        Product orange = productRepository.findByNameAndCart("Orange",null);
        if(orange==null){
            orange = new Product();
            orange.setName("Orange");
            orange.setDescription("It's a fruit.");
            orange.setPrice(100);
            orange.setImage("https://images.unsplash.com/photo-1580052614034-c55d20bfee3b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80");
            orange.setCart(null);
            productRepository.save(orange);
        }



        Product apple = productRepository.findByNameAndCart("Apple",null);
        if(apple==null){
            apple = new Product();
            apple.setName("Apple");
            apple.setDescription("It's a fruit.");
            apple.setPrice(100);
            apple.setImage("https://images.unsplash.com/photo-1570913149827-d2ac84ab3f9a?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2070&q=80");
            apple.setCart(null);
            productRepository.save(apple);
        }

        Product banana = productRepository.findByNameAndCart("Banana",null);
        if(banana==null){
            banana = new Product();
            banana.setName("Banana");
            banana.setDescription("It's a fruit.");
            banana.setPrice(100);
            banana.setImage("https://images.unsplash.com/photo-1587132137056-bfbf0166836e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80");
            banana.setCart(null);
            productRepository.save(banana);
        }

    }
}
