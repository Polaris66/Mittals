package com.example.mittals.Contollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.CustomRepositoryImplementationDetector;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import com.example.mittals.Entities.Cart;
import com.example.mittals.Entities.Product;
import com.example.mittals.Entities.User;
import com.example.mittals.Repositories.CartRepository;
import com.example.mittals.Repositories.ProductRepository;
import com.example.mittals.Repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

@Controller
public class ManagerCotroller {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/products")
    public String listProducts(Model model){
        List<Product> listProducts = productRepository.findAllByCartIsNull();
        model.addAttribute("listProducts",listProducts);
        return "products";
    }

    @GetMapping("add-products")
    public String showProductMakingForm(Model model)
    {
        model.addAttribute("product", new Product());
        return  "product_form";
    }

    @PostMapping("/make_product")
    public String makeProduct(Product product, Model model){
        product.setCart(null);
        productRepository.save(product);
        List<Product> listProducts = productRepository.findAllByCartIsNull();
        model.addAttribute("listProducts",listProducts);
        return "products";
    }

    @PostMapping("/process_remove_product")
    public String remove(Product product, Model model){
        productRepository.deleteById(product.getId());
        List<Product> listProducts = productRepository.findAllByCartIsNull();
        model.addAttribute("listProducts",listProducts);
        return "products";
    }






}
