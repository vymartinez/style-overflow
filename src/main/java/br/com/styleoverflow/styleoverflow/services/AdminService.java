package br.com.styleoverflow.styleoverflow.services;

import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Size;

public class AdminService {

    private final ProductService productService;

    public AdminService(ProductService productService) {
        this.productService = productService;
    }

    public void createProduct(String name, Size size, Gender gender, String color, Integer stock, Double price, String photoUrl) {
        productService.createProduct(name, size, gender, color, stock, price, photoUrl);
    }

    public void patchProduct(String name, Size size, Gender gender, String color, Integer stock, Double price, String photoUrl, Integer productId) {
        productService.updateProduct(name, size, gender, color, stock, price, photoUrl, productId);
    }

    public void deleteProduct(Integer productId) {
        productService.deleteProduct(productId);
    }
}
