package br.com.styleoverflow.styleoverflow.classes;

import java.util.List;

public class Cart {

    private final List<CartProduct> products;

    public Cart(List<CartProduct> products) {
        this.products = products;
    }

    public List<CartProduct> getProducts() {
        return products;
    }

    public void clear() {
        products.clear();
    }

    public void addProduct(CartProduct product) {
        products.add(product);
    }

    public void removeProduct(CartProduct product) {
        products.remove(product);
    }

    public double calculateTotal() {
        return products.stream().mapToDouble(CartProduct::getSubtotal).sum();
    }
}