package com.example.mittals.Contollers;

import com.example.mittals.Entities.Product;
import com.example.mittals.Entities.User;
import com.example.mittals.Repositories.ProductRepository;
import com.example.mittals.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AppController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductRepository productRepository;
    @GetMapping("")
    public String viewHomePage()
    {
        return "index";

    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model)
    {
     model.addAttribute("user", new User());

     return  "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setWallet(1000);
        userRepo.save(user);

        return "register_success";
    }

    @GetMapping("/users")
    public String listUsers(Model model){
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers",listUsers);

        return "users";
    }
    long id = 1;
    @GetMapping("/profile")
    public String showProfileForm(Model model){
        User user = userRepo.getReferenceById(id);
        model.addAttribute(user);
        return "profile";
    }

    @GetMapping("add-products")
    public String showProductMakingForm(Model model)
    {
        model.addAttribute("product", new Product());
        return  "product_form";
    }

    @PostMapping("/make_product")
    public String makeProduct(Product product){
        productRepository.save(product);
        return "register_success";
    }
}
