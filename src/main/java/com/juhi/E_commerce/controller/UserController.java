
package com.juhi.E_commerce.controller;

import com.juhi.E_commerce.model.User;
import com.juhi.E_commerce.repo.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String mobileNumber,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            RedirectAttributes redirectAttributes) {

        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute(
                    "error", "Passwords do not match!");
            return "redirect:/signup";
        }

        if (userRepository.existsByEmail(email)) {
            redirectAttributes.addFlashAttribute(
                    "error", "Email is already registered!");
            return "redirect:/signup";
        }

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setMobileNumber(mobileNumber);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);

        redirectAttributes.addFlashAttribute(
                "success", "Registration successful! Please login.");

        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null ||
                !passwordEncoder.matches(password, user.getPassword())) {
            redirectAttributes.addFlashAttribute(
                    "error", "Invalid email or password!");
            return "redirect:/login";
        }

        session.setAttribute(
                "loggedInUser",
                user.getFirstName() + " " + user.getLastName());

        return "redirect:/";
    }
}
