
package com.juhi.E_commerce.order;

import com.juhi.E_commerce.cart.CartItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CheckoutController {

    private final CustomerOrderRepository orderRepository;

    public CheckoutController(
            CustomerOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @SuppressWarnings("unchecked")
    private List<CartItem> getCart(HttpSession session) {
        return (List<CartItem>) session.getAttribute("cart");
    }

    // Checkout page
    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {

        List<CartItem> cart = getCart(session);

        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        double total = 0;

        for (CartItem item : cart) {
            total += item.getTotalPrice();
        }

        model.addAttribute("cartItems", cart);
        model.addAttribute("cartTotal", total);

        return "checkout";
    }

    // Place order
    @PostMapping("/place-order")
    public String placeOrder(
            @RequestParam String customerName,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam(defaultValue = "COD") String paymentMethod,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        List<CartItem> cart = getCart(session);

        if (cart == null || cart.isEmpty()) {
            return "redirect:/cart";
        }

        customerName = customerName.trim();
        phone = phone.trim();
        address = address.trim();

        if (customerName.isBlank()
                || phone.isBlank()
                || address.isBlank()) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Please fill in all delivery details."
            );

            return "redirect:/checkout";
        }

        if (!phone.matches("[0-9]{10}")) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Please enter a valid 10-digit mobile number."
            );

            return "redirect:/checkout";
        }

        if (!"COD".equals(paymentMethod)) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Please select Cash on Delivery."
            );

            return "redirect:/checkout";
        }

        CustomerOrder order = new CustomerOrder();

        order.setCustomerName(customerName);
        order.setPhone(phone);
        order.setAddress(address);
        order.setPaymentMethod("Cash on Delivery");
        order.setStatus("PLACED");

        double total = 0;

        for (CartItem cartItem : cart) {

            if (cartItem.getQuantity() < 1
                    || cartItem.getPrice() < 0) {

                redirectAttributes.addFlashAttribute(
                        "errorMessage",
                        "Invalid cart item. Please review your cart."
                );

                return "redirect:/cart";
            }

            OrderItem orderItem = new OrderItem(
                    cartItem.getProductName(),
                    cartItem.getImage(),
                    cartItem.getPrice(),
                    cartItem.getQuantity()
            );

            order.addItem(orderItem);
            total += orderItem.getLineTotal();
        }

        order.setTotalAmount(total);

        CustomerOrder savedOrder = orderRepository.save(order);

        // Clear cart after successful order save
        cart.clear();

        return "redirect:/order-confirmation/"
                + savedOrder.getId();
    }

    // Order confirmation page
    @GetMapping("/order-confirmation/{id}")
    public String orderConfirmation(
            @PathVariable Long id,
            Model model) {

        CustomerOrder order = orderRepository.findById(id)
                .orElse(null);

        if (order == null) {
            return "redirect:/products";
        }

        model.addAttribute("order", order);

        return "order-confirmation";
    }

    // Open Track Order page
    @GetMapping("/track-order")
    public String trackOrderPage() {
        return "track-order";
    }

    // Track a specific order using ID and phone
    @PostMapping("/track-order")
    public String trackOrder(
            @RequestParam Long orderId,
            @RequestParam String phone,
            Model model) {

        String cleanPhone = phone.trim();

        if (orderId < 1 || !cleanPhone.matches("[0-9]{10}")) {

            model.addAttribute(
                    "errorMessage",
                    "Please enter a valid Order ID and 10-digit mobile number."
            );

            return "track-order";
        }

        CustomerOrder order = orderRepository
                .findByIdAndPhone(orderId, cleanPhone)
                .orElse(null);

        if (order == null) {

            model.addAttribute(
                    "errorMessage",
                    "Order not found. Please check your Order ID and mobile number."
            );

            return "track-order";
        }

        model.addAttribute("order", order);

        return "track-order";
    }

    // Open My Orders page
    @GetMapping("/my-orders")
    public String myOrdersPage() {
        return "my-orders";
    }

    // Find all orders associated with the entered phone number
    @PostMapping("/my-orders")
    public String findMyOrders(
            @RequestParam String phone,
            Model model) {

        String cleanPhone = phone.trim();

        model.addAttribute("phone", cleanPhone);

        if (!cleanPhone.matches("[0-9]{10}")) {

            model.addAttribute(
                    "errorMessage",
                    "Please enter a valid 10-digit mobile number."
            );

            return "my-orders";
        }

        List<CustomerOrder> orders =
                orderRepository.findByPhoneOrderByCreatedAtDesc(
                        cleanPhone
                );

        model.addAttribute("orders", orders);

        if (orders.isEmpty()) {
            model.addAttribute(
                    "errorMessage",
                    "No orders found for this mobile number."
            );
        }

        return "my-orders";
    }
}
