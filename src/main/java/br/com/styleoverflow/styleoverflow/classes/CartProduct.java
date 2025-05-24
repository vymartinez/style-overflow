package br.com.styleoverflow.styleoverflow.classes;

public class CartProduct {
    private Product product;
    private Integer quantity;

    public CartProduct(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public Double calculateSubtotal() {
        return product.getPrice() * quantity;
    }
}
