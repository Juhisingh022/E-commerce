
package com.juhi.E_commerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;
import com.juhi.E_commerce.cart.CartItem;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/product")
    public String product() {
        return "product";
    }

    
@GetMapping("/view_product")
public String viewProduct(
        @RequestParam(name = "name", defaultValue = "Baby Clothes") String name,
        Model model) {

    Map<String, String> details = getProductDetails(name);

    model.addAttribute("productName", name);
    model.addAttribute("productCategory", details.get("category"));
    model.addAttribute("productImage", details.get("image"));
    model.addAttribute("productPrice", details.get("price"));
    model.addAttribute("productDiscount", details.get("discount"));
    model.addAttribute("productDescription", details.get("description"));

    return "view_product";
}

private Map<String, String> getProductDetails(String name) {
    Map<String, String> details = new java.util.HashMap<>();

    switch (name) {
        case "Baby Clothes":
        case "Baby Romper":
        case "Baby Dress Set":
            details.put("category", "Baby Clothes");
            details.put("image", "/img/girl1.jpg");
            details.put("description", "Comfortable and stylish baby clothing.");
            break;

        case "Birthday Gift":
        case "Gift Hamper":
        case "Special Gift Box":
            details.put("category", "Gifts");
            details.put("image", "/img/gift.jpg");
            details.put("description", "A thoughtful gift for your special occasions.");
            break;

        case "Casual Shoes":
        case "Comfort Sandals":
        case "Daily Wear Slippers":
            details.put("category", "Foot Wear");
            details.put("image", "/img/Footwear-.jpg");
            details.put("description", "Comfortable footwear for everyday use.");
            break;

        case "Fashion Jewellery":
        case "Jewellery Set":
        case "Daily Wear Accessories":
            details.put("category", "Accessary");
            details.put("image", "/img/jwellery.jpeg");
            details.put("description", "Stylish accessories for everyday wear.");
            break;

        case "Sling Bag":
        case "Everyday Handbag":
        case "Party Wear Bag":
            details.put("category", "Bags");
            details.put("image", "/img/SlingBagwebp.webp");
            details.put("description", "A stylish bag for your everyday needs.");
            break;

        case "Home Decor Item":
        case "Decorative Showpiece":
        case "Home Decoration Set":
            details.put("category", "Home Decore items");
            details.put("image", "/img/homedecore.jpg");
            details.put("description", "Decorative items to enhance your home.");
            break;

        case "Classic Watch":
        case "Fashion Watch":
        case "Digital Watch":
            details.put("category", "Watches");
            details.put("image", "/img/watchs.webp");
            details.put("description", "A stylish watch for everyday use.");
            break;

        case "Study Notes":
        case "Exam Preparation Material":
        case "Study Guide":
            details.put("category", "Study Materials");
            details.put("image", "/img/study.jpg");
            details.put("description", "Study material to support your preparation.");
            break;

        case "Electronics":
            details.put("category", "Electronics");
            details.put("image", "/img/elo1.jpeg");
            details.put("description", "Useful electronic products for everyday needs.");
            break;

        case "Mobile Phones":
            details.put("category", "Phones");
            details.put("image", "/img/mob.jpeg");
            details.put("description", "Mobile phones for communication and entertainment.");
            break;

        default:
            details.put("category", "Products");
            details.put("image", "/img/babycloths.jpeg");
            details.put("description", "Explore this product from NovaCart.");
            break;
    }

    // Current listing prices
    String price = "500";
    String discount = "20% off";

    switch (name) {
        case "Baby Clothes":
            price = "500"; discount = "50% off"; break;
        case "Baby Romper":
            price = "399"; discount = "20% off"; break;
        case "Baby Dress Set":
            price = "799"; discount = "25% off"; break;
        case "Birthday Gift":
            price = "499"; discount = "10% off"; break;
        case "Gift Hamper":
            price = "899"; discount = "15% off"; break;
        case "Special Gift Box":
            price = "699"; discount = "20% off"; break;
        case "Casual Shoes":
            price = "999"; discount = "20% off"; break;
        case "Comfort Sandals":
            price = "599"; discount = "15% off"; break;
        case "Daily Wear Slippers":
            price = "299"; discount = "10% off"; break;
        case "Fashion Jewellery":
            price = "399"; discount = "20% off"; break;
        case "Jewellery Set":
            price = "799"; discount = "25% off"; break;
        case "Daily Wear Accessories":
            price = "249"; discount = "10% off"; break;
        case "Sling Bag":
            price = "699"; discount = "20% off"; break;
        case "Everyday Handbag":
            price = "999"; discount = "15% off"; break;
        case "Party Wear Bag":
            price = "1199"; discount = "25% off"; break;
        case "Home Decor Item":
            price = "499"; discount = "10% off"; break;
        case "Decorative Showpiece":
            price = "799"; discount = "15% off"; break;
        case "Home Decoration Set":
            price = "999"; discount = "20% off"; break;
        case "Classic Watch":
            price = "999"; discount = "15% off"; break;
        case "Fashion Watch":
            price = "1499"; discount = "20% off"; break;
        case "Digital Watch":
            price = "1299"; discount = "10% off"; break;
        case "Study Notes":
            price = "199"; discount = "10% off"; break;
        case "Exam Preparation Material":
            price = "399"; discount = "15% off"; break;
        case "Study Guide":
            price = "299"; discount = "10% off"; break;
        case "Electronics":
            price = "2100"; discount = "20% off"; break;
        case "Mobile Phones":
            price = "4500"; discount = "30% off"; break;
    }

    details.put("price", price);
    details.put("discount", discount);

    return details;
}


    @ModelAttribute
    public void addCartCount(HttpSession session, Model model) {

        List<CartItem> cart =
                (List<CartItem>) session.getAttribute("cart");

        int count = 0;

        if (cart != null) {
            for (CartItem item : cart) {
                count += item.getQuantity();
            }
        }

        model.addAttribute("cartCount", count);
    }
}
