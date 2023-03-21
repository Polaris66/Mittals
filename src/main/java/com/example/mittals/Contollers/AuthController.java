package com.example.mittals.Contollers;

import com.example.mittals.Entities.Cart;
import com.example.mittals.Entities.User;
import com.example.mittals.Repositories.CartRepository;
import com.example.mittals.Repositories.UserRepository;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AuthController {
    
    User current_user = null;

    Boolean loggedIn = false;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/signup")
    public String showRegistrationForm(Model model)
    {
     model.addAttribute("user", new User());

     return  "signup_form";
    }
    @GetMapping("/add-users")
    public String showUserForm(Model model)
    {
        model.addAttribute("user", new User());
        return  "add_users";
    }
    @PostMapping("/process_add_user")
    public String addUser(User user){
        user.setWallet(1000);
        Cart cart = new Cart();
        userRepository.save(user);
        cart.setCustomerId(user.getId());
        cartRepository.save(cart);
        return "admin";
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
                    current_user = og_user;
                    loggedIn = true;
                    if(current_user.getRole()==0) {
                        return "customer";
                    }
                    if(current_user.getRole()==1) {
                        return "manager";
                    }
                    if(current_user.getRole()==2){
                        return "admin";
                    }
                    return "login_form";
            }
        }
        model.addAttribute("errorMessage", "Invalid Credentials"); 
        return "login_form";
    }
    @PostMapping("/process_register")
    public String processRegister(User user, Model model){
        User og_user = userRepository.findByEmail(user.getEmail());
        if(og_user==null){
            user.setRole(0);
            user.setWallet(1000);

            Cart cart = new Cart();
            cart.setCustomerId(user.getId());
            cartRepository.save(cart);
            userRepository.save(user);
            return "login_form";
        }
        model.addAttribute("errorMessage", "User already exists."); 
        return "signup_form";

    }
}