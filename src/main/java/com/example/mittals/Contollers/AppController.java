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

import java.util.List;

@Controller
public class AppController {

    User current_user = null;

    Boolean loggedIn = false;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;
    @GetMapping("")
    public String viewHomePage()
    {
        return "index";

    }
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
        userRepository.save(user);
        return "admin";
    }

    @GetMapping("/signin")
    public String showLoginForm(Model model)
    {
        model.addAttribute("user", new User());
        return  "login_form";
    }

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/process_login")
    public String processLogin(User user){
        User og_user = userRepository.findByEmail(user.getEmail());

        if(og_user!=null) {
            if (og_user.getPassword().equals(user.getPassword())) {
                if (og_user.getRole() == user.getRole()) {
                    current_user = og_user;
                    loggedIn = true;
                    if(user.getRole()==0) {
                        return "products";
                    }
                    if(user.getRole()==1) {
                        return "manager";
                    }
                    if(user.getRole()==2){
                        return "admin";
                    }
                    return "products";
                }
            }
        } return "login_form";
    }
    @PostMapping("/process_register")
    public String processRegister(User user){

        user.setRole(0);
        user.setWallet(1000);
        userRepository.save(user);
        return "register_success";
    }

    @GetMapping("/customers")
    public String listUsers(Model model){
        List<User> listUsers = userRepository.findAllByRole(1);
        model.addAttribute("listUsers",listUsers);
        return "customers";
    }

    @GetMapping("/managers")
    public String listManagers(Model model){
        List<User> listUsers = userRepository.findAllByRole(1);
        model.addAttribute("listUsers",listUsers);
        return "managers";
    }
    long id = 1;
    @GetMapping("/profile")
    public String showProfileForm(Model model){
        User user = userRepository.getReferenceById(id);
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
        if(current_user.getRole()==1) {
            return "manager";
        }
        if(current_user.getRole()==2) {
            return "admin";
        }
        return "manager";
    }

    @GetMapping("/products")
    public String listProducts(Model model){
        List<Product> listProducts = productRepository.findAll();
        model.addAttribute("listProducts",listProducts);
        return "products";
    }
    @GetMapping("/products_manager")
    public String listProducts_manager(Model model){
        List<Product> listProducts = productRepository.findAll();
        model.addAttribute("listProducts",listProducts);
        return "products_manager";
    }

    @GetMapping("/remove_products")
    public String RemoveProducts(Model model)
    {   Long id = 0L;
        model.addAttribute("product", new Product());
        return "remove_product";
    }


    @PostMapping("/process_remove_product")
    public String remove(Product product){
        productRepository.deleteById(product.getId());
        if(current_user.getRole()==1) {
            return "manager";
        }
        if(current_user.getRole()==2) {
            return "admin";
        }
        return "manager";
    }

    @GetMapping("/remove_users")
    public String RemoveUsers(Model model)
    {   Long id = 0L;
        model.addAttribute("user", new User());
        return "remove_user";
    }

    @PostMapping("/process_remove_user")
    public String remove_user(User user){
        userRepository.deleteById(user.getId());
        if(current_user.getRole()==1) {
            return "manager";
        }
        if(current_user.getRole()==2) {
            return "admin";
        }
        return "manager";
    }
}
