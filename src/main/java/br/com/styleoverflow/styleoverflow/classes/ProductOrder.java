package br.com.styleoverflow.styleoverflow.classes;

public class ProductOrder {
    private Product product;
    private Integer quantity;

    public ProductOrder(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double calculateSubtotal() {
        return product.getPrice() * quantity;
    }
}
