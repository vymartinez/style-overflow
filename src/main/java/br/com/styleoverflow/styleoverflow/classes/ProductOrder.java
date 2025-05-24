package br.com.styleoverflow.styleoverflow.classes;

public class ProductOrder {
    private Product product;
    private Integer quantity;

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

    public void patchStock() {
        int updatedStock = product.getStock() - quantity;
        product.setStock(updatedStock);
    }
}
