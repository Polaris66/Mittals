package com.example.mittals.Contollers;

import com.example.mittals.Entities.Customer;
import com.example.mittals.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {
    @Autowired
    private CustomerRepository customerRepo;

    @GetMapping("")
    public String viewHomePage()
    {
        return "index";

    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model)
    {
     model.addAttribute("customer", new Customer());

     return  "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(Customer customer){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
        customerRepo.save(customer);

        return "register_success";
    }
}
