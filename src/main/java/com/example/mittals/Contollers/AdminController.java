package com.example.mittals.Contollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import com.example.mittals.Entities.Cart;
import com.example.mittals.Entities.User;
import com.example.mittals.Repositories.CartRepository;
import com.example.mittals.Repositories.UserRepository;

@Controller
public class AdminController {

    User current_user = userDetails.currentUser;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;
    
    @GetMapping("/users")
    public String listUsers(Model model){
        List<User> listUsers = userRepository.findAllByRole(1);
        listUsers.addAll(userRepository.findAllByRole(0));
        model.addAttribute("listUsers",listUsers);
        return "users";
    }

    @GetMapping("/add-users")
    public String showUserForm(Model model)
    {
        model.addAttribute("user", new User());
        return "add_users";
    }
    @PostMapping("/process_add_user")
    public String addUser(User user, Model model){
        user.setWallet(1000);
        Cart cart = new Cart();
        user.setCart(cart);
        userRepository.save(user);
        cartRepository.save(cart);
        List<User> listUsers = userRepository.findAllByRole(1);
        listUsers.addAll(userRepository.findAllByRole(0));
        model.addAttribute("listUsers",listUsers);
        return "users";
    }

    @PostMapping("/process_remove_user")
    public String remove_user(User user, Model model){
        userRepository.deleteById(user.getId());
        List<User> listUsers = userRepository.findAllByRole(1);
        listUsers.addAll(userRepository.findAllByRole(0));
        model.addAttribute("listUsers",listUsers);
        return "users";
    }
}
