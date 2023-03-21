package com.example.mittals.Contollers;

import com.example.mittals.Entities.Cart;
import com.example.mittals.Entities.Product;
import com.example.mittals.Entities.User;
import com.example.mittals.Repositories.CartRepository;
import com.example.mittals.Repositories.ProductRepository;
import com.example.mittals.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AppController {

    User current_user = null;

    Boolean loggedIn = false;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("")
    public String viewHomePage()
    {
        loggedIn = false;
        return "index";
    }
    
    @GetMapping("/customers")
    public String listUsers(Model model){
        List<User> listUsers = userRepository.findAllByRole(0);
        model.addAttribute("listUsers",listUsers);
        return "customers";
    }


    @GetMapping("/customer")
    public String Customer(Model model){
        return "customer";
    }
    @GetMapping("/managers")
    public String listManagers(Model model){
        List<User> listUsers = userRepository.findAllByRole(1);
        model.addAttribute("listUsers",listUsers);
        return "managers";
    }

    @GetMapping("/profile")
    public String showProfileForm(Model model){
        User user = userRepository.getReferenceById(current_user.getId());
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
        product.setCart(cartRepository.getReferenceById(2L));
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
        List<Product> listProducts = productRepository.findAllByCart(cartRepository.getReferenceById(2L));
        model.addAttribute("listProducts",listProducts);
        return "products";
    }


    @GetMapping("/cart")
    public String viewProducts(Model model){
        Cart cart = cartRepository.findByCustomerId(current_user.getId());
        List<Product> listProducts = productRepository.findAllByCart(cart);
        model.addAttribute("listProducts",listProducts);
        int total_Price = 0;
        for(int i = 0; i < listProducts.size(); i++){
            total_Price+=listProducts.get(i).getPrice();
        }
        model.addAttribute("total",total_Price );
        return "cart";
    }
    @GetMapping("/add_to_cart")
    public String AddToCart(Model model){
        model.addAttribute("product", new Product());
        return "add_to_cart";
    }


    @PostMapping("/final_addition_to_cart")
    public String finalAddToCart(Product product){
        Product p1 = productRepository.findAllByName(product.getName()).get(0);
        product.setDescription(p1.getDescription());
        product.setImage(p1.getImage());
        product.setPrice(p1.getPrice());
        product.setCart(cartRepository.findByCustomerId(current_user.getId()));
        productRepository.save(product);
        return  "customer";
    }


    @GetMapping("/products_manager")
    public String listProducts_manager(Model model){
        Cart cart = cartRepository.findByCustomerId(current_user.getId());
        List<Product> listProducts = productRepository.findAllByCart(cart);
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
