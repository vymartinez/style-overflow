package br.com.styleoverflow.styleoverflow.classes;

import java.util.List;

public class Cart {

    private int id;
    private List<CartProduct> products;

    public Cart(int id, List<CartProduct> products) {
        this.id = id;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<CartProduct> getProducts() {
        return products;
    }

    public void setProducts(List<CartProduct> products) {
        this.products = products;
    }

    //public double calculateTotal() {
    //    return products.stream().mapToDouble(CartProduct::calculateSubtotal).sum();
    //}
}