package com.example.mittals.Contollers;

import com.example.mittals.Entities.Product;
import com.example.mittals.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping("/")
    public String viewHomePage(Model model)
    {   if(userDetails.loggedIn == false || userDetails.currentUser.getRole()==0){
            List<Product> listProducts = productRepository.findAllByCartIsNull();
            model.addAttribute("listProducts",listProducts);
            model.addAttribute("loggedIn", userDetails.loggedIn);
            return "index";
        }
        else if(userDetails.currentUser.getRole()==1)
        return "manager";
        else
        return "admin";
    }
}
