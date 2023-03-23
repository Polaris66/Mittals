package com.example.mittals.Contollers;

import com.example.mittals.Entities.Cart;
import com.example.mittals.Entities.Product;
import com.example.mittals.Entities.User;
import com.example.mittals.Repositories.CartRepository;
import com.example.mittals.Repositories.ProductRepository;
import com.example.mittals.Repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AuthController {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/signup")
    public String showRegistrationForm(Model model)
    {
     model.addAttribute("user", new User());
     return  "signup_form";
    }
    
    @PostMapping("/process_register")
    public String processRegister(User user, Model model){
        User og_user = userRepository.findByEmail(user.getEmail());
        if(og_user==null){
            user.setRole(0);
            user.setWallet(1000);

            Cart cart = new Cart();
            cartRepository.save(cart);
            user.setCart(cart);
            userRepository.save(user);
            return "login_form";
        }
        model.addAttribute("errorMessage", "User already exists."); 
        return "signup_form";

    }

    @GetMapping("/signin")
    public String showLoginForm(Model model)
    {
        model.addAttribute("user", new User());
        return  "login_form";
    }

    @PostMapping("/process_login")
    public String processLogin(User user, Model model){
        User og_user = userRepository.findByEmail(user.getEmail());
        if(og_user!=null) {
            if (og_user.getPassword().equals(user.getPassword())) {
                    userDetails.loggedIn = true;
                    userDetails.currentUser = og_user;
                    
                        
                    //new user nhi ghus rha
                    if(userDetails.currentUser.getRole()==0) {
                        List<Product> listProducts = productRepository.findAllByCartIsNull();
                        model.addAttribute("listProducts",listProducts);
                        model.addAttribute("loggedIn", userDetails.loggedIn);
                        return "index";
                    }
                    if(userDetails.currentUser.getRole()==1) {
                        return "manager";
                    }
                    if(userDetails.currentUser.getRole()==2){
                        return "admin";
                    }
                    return "login_form";
            }
        }
        model.addAttribute("errorMessage", "Invalid Credentials"); 
        return "login_form";
    }


    @GetMapping("/signout")
    public String sign_out(Model model)
    {
        userDetails.loggedIn= false;
        userDetails.currentUser= null;
        List<Product> listProducts = productRepository.findAllByCartIsNull();
        model.addAttribute("listProducts",listProducts);
        model.addAttribute("loggedIn", userDetails.loggedIn);   
        return "index";

    }
 
}