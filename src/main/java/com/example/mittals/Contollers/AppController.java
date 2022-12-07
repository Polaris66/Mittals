package com.example.mittals.Contollers;

import com.example.mittals.Entities.Customer;
import com.example.mittals.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {
    @Autowired
    private CustomerRepository customerRepo;

    @GetMapping("/home")
    public String viewHomePage()
    {

        return "notSignedIn";

    }



    @GetMapping("/register")
    public String showRegistrationForm(Model model)
    {
     model.addAttribute("customer", new Customer());

     return  "signup_form";
    }

    long id_no=7;


    @RequestMapping("/profile")
    public String showProfileForm(Model model)
    {
        Customer customer1=customerRepo.getReferenceById(id_no);
        model.addAttribute(customer1);
        if(id_no!=-1) {
            return "profile_form";
        } else {
            return "notSignedIn";
        }
    }



    @PostMapping("/process_register")
    public String processRegister(Customer customer){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
        customer.setWallet(1000);
        customerRepo.save(customer);

        return "register_success";
    }


}
