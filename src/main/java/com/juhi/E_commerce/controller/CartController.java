
package com.juhi.E_commerce.controller;

import com.juhi.E_commerce.cart.CartItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @SuppressWarnings("unchecked")
    private List<CartItem> getCart(HttpSession session) {
        List<CartItem> cart =
                (List<CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        return cart;
    }

    // ADD PRODUCT TO CART
    @PostMapping("/add-to-cart")
    public String addToCart(
            @RequestParam(defaultValue = "Baby Clothes") String productName,
            HttpSession session) {

        List<CartItem> cart = getCart(session);

        String description;
        String image;
        double price;

        switch (productName) {
            case "Baby Clothes":
                description = "Comfortable and stylish baby clothing.";
                image = "/img/girl1.jpg";
                price = 500;
                break;
            case "Baby Romper":
                description = "Comfortable baby romper.";
                image = "/img/girl1.jpg";
                price = 399;
                break;
            case "Baby Dress Set":
                description = "Stylish baby dress set.";
                image = "/img/girl1.jpg";
                price = 799;
                break;

            case "Birthday Gift":
                description = "A thoughtful birthday gift.";
                image = "/img/gift.jpg";
                price = 499;
                break;
            case "Gift Hamper":
                description = "A special gift hamper.";
                image = "/img/gift.jpg";
                price = 899;
                break;
            case "Special Gift Box":
                description = "A gift box for special occasions.";
                image = "/img/gift.jpg";
                price = 699;
                break;

            case "Casual Shoes":
                description = "Comfortable casual shoes.";
                image = "/img/Footwear-.jpg";
                price = 999;
                break;
            case "Comfort Sandals":
                description = "Comfortable everyday sandals.";
                image = "/img/Footwear-.jpg";
                price = 599;
                break;
            case "Daily Wear Slippers":
                description = "Slippers for everyday use.";
                image = "/img/Footwear-.jpg";
                price = 299;
                break;

            case "Fashion Jewellery":
                description = "Stylish fashion jewellery.";
                image = "/img/jwellery.jpeg";
                price = 399;
                break;
            case "Jewellery Set":
                description = "A stylish jewellery set.";
                image = "/img/jwellery.jpeg";
                price = 799;
                break;
            case "Daily Wear Accessories":
                description = "Accessories for everyday wear.";
                image = "/img/jwellery.jpeg";
                price = 249;
                break;

            case "Sling Bag":
                description = "A stylish sling bag.";
                image = "/img/SlingBagwebp.webp";
                price = 699;
                break;
            case "Everyday Handbag":
                description = "A handbag for everyday use.";
                image = "/img/SlingBagwebp.webp";
                price = 999;
                break;
            case "Party Wear Bag":
                description = "A stylish party wear bag.";
                image = "/img/SlingBagwebp.webp";
                price = 1199;
                break;

            case "Home Decor Item":
                description = "Decorative item for your home.";
                image = "/img/homedecore.jpg";
                price = 499;
                break;
            case "Decorative Showpiece":
                description = "A decorative showpiece.";
                image = "/img/homedecore.jpg";
                price = 799;
                break;
            case "Home Decoration Set":
                description = "A set of home decoration items.";
                image = "/img/homedecore.jpg";
                price = 999;
                break;

            case "Classic Watch":
                description = "A classic everyday watch.";
                image = "/img/watchs.webp";
                price = 999;
                break;
            case "Fashion Watch":
                description = "A fashionable wristwatch.";
                image = "/img/watchs.webp";
                price = 1499;
                break;
            case "Digital Watch":
                description = "A digital watch.";
                image = "/img/watchs.webp";
                price = 1299;
                break;

            case "Study Notes":
                description = "Notes to support your studies.";
                image = "/img/study.jpg";
                price = 199;
                break;
            case "Exam Preparation Material":
                description = "Material for exam preparation.";
                image = "/img/study.jpg";
                price = 399;
                break;
            case "Study Guide":
                description = "A guide to support your studies.";
                image = "/img/study.jpg";
                price = 299;
                break;

            case "Electronics":
                description = "Electronic product for everyday use.";
                image = "/img/elo1.jpeg";
                price = 2100;
                break;
            case "Mobile Phones":
                description = "Mobile phone for communication.";
                image = "/img/mob.jpeg";
                price = 4500;
                break;

            default:
                return "redirect:/products";
        }

        CartItem existingItem = null;

        for (CartItem item : cart) {
            if (item.getProductName().equals(productName)) {
                existingItem = item;
                break;
            }
        }

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
        } else {
            cart.add(new CartItem(
                    productName,
                    description,
                    image,
                    price,
                    1
            ));
        }

        return "redirect:/cart";
    }

    // SHOW CART
    @GetMapping("/cart")
    public String showCart(HttpSession session, Model model) {
        List<CartItem> cart = getCart(session);

        int cartCount = 0;
        double cartTotal = 0;

        for (CartItem item : cart) {
            cartCount += item.getQuantity();
            cartTotal += item.getTotalPrice();
        }

        model.addAttribute("cartItems", cart);
        model.addAttribute("cartCount", cartCount);
        model.addAttribute("cartTotal", cartTotal);

        return "cart";
    }

    // INCREASE OR DECREASE QUANTITY
    @PostMapping("/update-cart")
    public String updateCart(
            @RequestParam("productName") String productName,
            @RequestParam("change") int change,
            HttpSession session) {

        // Accept only +1 or -1 changes
        if (change != 1 && change != -1) {
            return "redirect:/cart";
        }

        List<CartItem> cart = getCart(session);

        for (CartItem item : cart) {
            if (item.getProductName().equals(productName)) {

                int newQuantity = item.getQuantity() + change;

                // Quantity cannot go below 1
                if (newQuantity >= 1) {
                    item.setQuantity(newQuantity);
                }

                break;
            }
        }

        return "redirect:/cart";
    }

    // REMOVE PRODUCT
    @PostMapping("/remove-from-cart")
    public String removeFromCart(
            @RequestParam("productName") String productName,
            HttpSession session) {

        List<CartItem> cart = getCart(session);

        cart.removeIf(item ->
                item.getProductName().equals(productName));

        return "redirect:/cart";
    }
}

