
package com.juhi.E_commerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    private final List<Map<String, String>> allProducts = List.of(

        // BABY CLOTHES
        Map.of("name", "Baby Clothes", "category", "Baby Clothes",
                "image", "/img/babycloths.jpeg", "price", "500", "discount", "50% off"),
        Map.of("name", "Baby Romper", "category", "Baby Clothes",
                "image", "/img/babycloths.jpeg", "price", "399", "discount", "20% off"),
        Map.of("name", "Baby Dress Set", "category", "Baby Clothes",
                "image", "/img/babycloths.jpeg", "price", "799", "discount", "25% off"),

        // GIFTS
        Map.of("name", "Birthday Gift", "category", "Gifts",
                "image", "/img/gift.jpg", "price", "499", "discount", "10% off"),
        Map.of("name", "Gift Hamper", "category", "Gifts",
                "image", "/img/gift.jpg", "price", "899", "discount", "15% off"),
        Map.of("name", "Special Gift Box", "category", "Gifts",
                "image", "/img/gift.jpg", "price", "699", "discount", "20% off"),

        // FOOTWEAR
        Map.of("name", "Casual Shoes", "category", "Foot Wear",
                "image", "/img/Footwear-.jpg", "price", "999", "discount", "20% off"),
        Map.of("name", "Comfort Sandals", "category", "Foot Wear",
                "image", "/img/Footwear-.jpg", "price", "599", "discount", "15% off"),
        Map.of("name", "Daily Wear Slippers", "category", "Foot Wear",
                "image", "/img/Footwear-.jpg", "price", "299", "discount", "10% off"),

        // ACCESSORIES
        Map.of("name", "Fashion Jewellery", "category", "Accessary",
                "image", "/img/jwellery.jpeg", "price", "399", "discount", "20% off"),
        Map.of("name", "Jewellery Set", "category", "Accessary",
                "image", "/img/jwellery.jpeg", "price", "799", "discount", "25% off"),
        Map.of("name", "Daily Wear Accessories", "category", "Accessary",
                "image", "/img/jwellery.jpeg", "price", "249", "discount", "10% off"),

        // BAGS
        Map.of("name", "Sling Bag", "category", "Bags",
                "image", "/img/SlingBagwebp.webp", "price", "699", "discount", "20% off"),
        Map.of("name", "Everyday Handbag", "category", "Bags",
                "image", "/img/SlingBagwebp.webp", "price", "999", "discount", "15% off"),
        Map.of("name", "Party Wear Bag", "category", "Bags",
                "image", "/img/SlingBagwebp.webp", "price", "1199", "discount", "25% off"),

        // HOME DECOR
        Map.of("name", "Home Decor Item", "category", "Home Decore items",
                "image", "/img/homedecore.jpg", "price", "499", "discount", "10% off"),
        Map.of("name", "Decorative Showpiece", "category", "Home Decore items",
                "image", "/img/homedecore.jpg", "price", "799", "discount", "15% off"),
        Map.of("name", "Home Decoration Set", "category", "Home Decore items",
                "image", "/img/homedecore.jpg", "price", "999", "discount", "20% off"),

        // WATCHES
        Map.of("name", "Classic Watch", "category", "Watches",
                "image", "/img/watchs.webp", "price", "999", "discount", "15% off"),
        Map.of("name", "Fashion Watch", "category", "Watches",
                "image", "/img/watchs.webp", "price", "1499", "discount", "20% off"),
        Map.of("name", "Digital Watch", "category", "Watches",
                "image", "/img/watchs.webp", "price", "1299", "discount", "10% off"),

        // STUDY MATERIALS
        Map.of("name", "Study Notes", "category", "Study Materials",
                "image", "/img/study.jpg", "price", "199", "discount", "10% off"),
        Map.of("name", "Exam Preparation Material", "category", "Study Materials",
                "image", "/img/study.jpg", "price", "399", "discount", "15% off"),
        Map.of("name", "Study Guide", "category", "Study Materials",
                "image", "/img/study.jpg", "price", "299", "discount", "10% off"),

        // EXISTING ELECTRONICS AND PHONES
        Map.of("name", "Electronics", "category", "Electronics",
                "image", "/img/elo1.jpeg", "price", "2100", "discount", "20% off"),
        Map.of("name", "Mobile Phones", "category", "Phones",
                "image", "/img/mob.jpeg", "price", "4500", "discount", "30% off")
    );

    @GetMapping("/products")
    public String products(
            @RequestParam(defaultValue = "") String category,
            @RequestParam(name = "ch", defaultValue = "") String search,
            Model model) {

        List<Map<String, String>> filteredProducts = allProducts.stream()
                .filter(p -> category.isBlank()
                        || p.get("category").equalsIgnoreCase(category.trim()))
                .filter(p -> search.isBlank()
                        || p.get("name").toLowerCase()
                        .contains(search.trim().toLowerCase()))
                .collect(Collectors.toList());

        model.addAttribute("products", filteredProducts);
        model.addAttribute("category", category);
        model.addAttribute("searchQuery", search);

        return "product";
    }
}
