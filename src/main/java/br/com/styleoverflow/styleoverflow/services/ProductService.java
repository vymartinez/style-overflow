package br.com.styleoverflow.styleoverflow.services;

import java.sql.Connection;
import java.util.List;

import br.com.styleoverflow.styleoverflow.ConnectionFactory;
import br.com.styleoverflow.styleoverflow.DTO.ProductDTO;
import br.com.styleoverflow.styleoverflow.DomainException;
import br.com.styleoverflow.styleoverflow.classes.Product;
import br.com.styleoverflow.styleoverflow.dao.ProductDAO;
import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Size;

public class ProductService {

    private static final ConnectionFactory factory = new ConnectionFactory();

    public void createProduct(String name, Size size, Gender gender, String color, Integer stock, Double price, String photoUrl) {
        Connection connection = factory.getConnection();

        if (name.isEmpty()) throw new DomainException("O produto deve ter um nome.");

        if (color.isEmpty()) throw new DomainException("O produto deve ter uma cor.");

        if (stock < 0) throw new DomainException("O produto deve ter um quantidade maior ou igual a zero.");

        if (price <= 0) throw new DomainException("O produto deve ter um valor maior que zero.");

        new ProductDAO(connection).createProduct(new ProductDTO(name, size, price, gender, color, stock, photoUrl));
    }

    public static List<Product> getAllProducts() {
        Connection connection = factory.getConnection();

        return new ProductDAO(connection).getAllProducts();
    }

    public static Product getProductById(int productId) {
        Connection connection = factory.getConnection();

        return new ProductDAO(connection).getProductById(productId);
    }

    public void updateProduct(String name, Size size, Gender gender, String color, Integer stock, Double price, String photoUrl, Integer productId) {
        Connection connection = factory.getConnection();

        Product product = new ProductDAO(connection).getProductById(productId);

        if (product == null) throw new DomainException("O produto não existe.");

        if (name.isEmpty()) throw new DomainException("O produto deve ter um nome.");

        if (color.isEmpty()) throw new DomainException("O produto deve ter uma cor.");

        if (stock < 0) throw new DomainException("O produto deve ter um quantidade maior ou igual a zero.");

        if (price <= 0) throw new DomainException("O produto deve ter um valor maior que zero.");

        connection = factory.getConnection();
        new ProductDAO(connection).updateProduct(new ProductDTO(name, size, price, gender, color, stock, photoUrl), productId);

    }

    public void deleteProduct(Integer productId) {
        Connection connection = factory.getConnection();

        Product product = new ProductDAO(connection).getProductById(productId);

        if (product == null) throw new DomainException("O produto não existe.");

        connection = factory.getConnection();
        new ProductDAO(connection).deleteProduct(productId);
    }
}
