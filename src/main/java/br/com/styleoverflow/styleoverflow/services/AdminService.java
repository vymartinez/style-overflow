package br.com.styleoverflow.styleoverflow.services;

import br.com.styleoverflow.styleoverflow.DomainException;
import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Role;
import br.com.styleoverflow.styleoverflow.enums.Size;
import br.com.styleoverflow.styleoverflow.classes.User;

public class AdminService {

    private final ProductService productService = new ProductService();

    public void createProduct(String name, Size size, Gender gender, String color, Integer stock, Double price, String photoUrl, User currentUser) {
        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            throw new DomainException("Somente administradores podem criar produtos.");
        }
        productService.createProduct(name, size, gender, color, stock, price, photoUrl);
    }

    public void patchProduct(String name, Size size, Gender gender, String color, Integer stock, Double price, String photoUrl, Integer productId, User currentUser) {
        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            throw new DomainException("Somente administradores podem editar produtos.");
        }
        productService.updateProduct(name, size, gender, color, stock, price, photoUrl, productId);
    }

    public void deleteProduct(Integer productId, User currentUser) {
        if (currentUser == null || currentUser.getRole() != Role.ADMIN) {
            throw new DomainException("Somente administradores podem excluir produtos.");
        }
        productService.deleteProduct(productId);
    }
}
