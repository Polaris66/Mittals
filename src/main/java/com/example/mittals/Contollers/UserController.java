package com.example.mittals.Contollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.mittals.Entities.Cart;
import com.example.mittals.Entities.Product;
import com.example.mittals.Entities.User;
import com.example.mittals.Repositories.CartRepository;
import com.example.mittals.Repositories.ProductRepository;
import com.example.mittals.Repositories.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    

    @GetMapping("/profile")
    public String showProfileForm(Model model){

        model.addAttribute(userDetails.currentUser);
        return "profile";
    }

    @PostMapping("/add-to-cart")
    public String addToCart(Product product, Model model){
        if(userDetails.currentUser==null){
            List<Product> listProducts = productRepository.findAllByCartIsNull();
            model.addAttribute("listProducts",listProducts);
            model.addAttribute("loggedIn", userDetails.loggedIn);
            model.addAttribute("errorMessage", "Login Required");
            return "index"; 
        }

        Cart cart = userDetails.currentUser.getCart();
        List<Product> listProductsInCart = productRepository.findAllByCart(cart);
        Product productInCart = null;
        Product productInRepo = productRepository.findById(product.getId()).get();
        for(int i = 0; i < listProductsInCart.size(); i++){
            if(listProductsInCart.get(i).getName().equals(productInRepo.getName())){
                productInCart = listProductsInCart.get(i);
                productRepository.deleteById(productInCart.getId());
                Product newProductInCart = new Product();
                newProductInCart.setName(productInCart.getName());
                newProductInCart.setPrice(productInRepo.getPrice());
                newProductInCart.setDescription(productInRepo.getDescription());
                newProductInCart.setImage(productInRepo.getImage());
                newProductInCart.setQuantity(productInCart.getQuantity()+1);
                newProductInCart.setCart(cart);
                productRepository.save(newProductInCart);
            }
        }
        if(productInCart==null){
            productInCart = new Product();
            productInCart.setName(productInRepo.getName());
            productInCart.setPrice(productInRepo.getPrice());
            productInCart.setDescription(productInRepo.getDescription());
            productInCart.setImage(productInRepo.getImage());
            productInCart.setQuantity(1);
            productInCart.setCart(cart);
            productRepository.save(productInCart);
        }

        List<Product> listProducts = productRepository.findAllByCartIsNull();
        model.addAttribute("listProducts",listProducts);
        model.addAttribute("loggedIn", userDetails.loggedIn);
        model.addAttribute("errorMessage", "Added to Cart"); 
        return "index";
    }

    @GetMapping("/cart")
    public String viewProducts(Model model){
        Cart cart = userDetails.currentUser.getCart();
        
        List<Product> listProducts = productRepository.findAllByCart(cart);
        model.addAttribute("listProducts",listProducts);
        int total_Price = 0;
        for(int i = 0; i < listProducts.size(); i++){
            total_Price+=listProducts.get(i).getPrice()*listProducts.get(i).getQuantity();
        }
        model.addAttribute("total",total_Price);
        model.addAttribute("wallet", userDetails.currentUser.getWallet());
        return "cart";
    }

    @GetMapping("/check-out")
    public String checkOut(Model model){
        Cart cart = userDetails.currentUser.getCart();
        List<Product> listProducts = productRepository.findAllByCart(cart);
        int total_Price = 0;
        for(int i = 0; i < listProducts.size(); i++){
            total_Price+=listProducts.get(i).getPrice()*listProducts.get(i).getQuantity();
        }
        if(total_Price<userDetails.currentUser.getWallet()){
            userDetails.currentUser.setWallet(userDetails.currentUser.getWallet()-total_Price);
        }
        else{
            model.addAttribute("listProducts",listProducts);
            model.addAttribute("total",total_Price);
            model.addAttribute("errorMessage", "Not Enough Money");
            model.addAttribute("wallet", userDetails.currentUser.getWallet());
            return "cart"; 
        }
        for(int i = 0; i < listProducts.size(); i++){
            productRepository.deleteById(listProducts.get(i).getId());
        }
        listProducts = productRepository.findAllByCart(cart);
        model.addAttribute("listProducts",listProducts);
        model.addAttribute("total",0);
        model.addAttribute("wallet", userDetails.currentUser.getWallet());
        return "cart";
    }
}
