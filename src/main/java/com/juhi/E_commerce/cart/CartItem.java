
package com.juhi.E_commerce.cart;

public class CartItem {

    private String productName;
    private String description;
    private String image;
    private double price;
    private int quantity;

    public CartItem() {
    }

    public CartItem(String productName, String description,
                    String image, double price, int quantity) {
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }
}
