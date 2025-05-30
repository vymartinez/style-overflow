package br.com.styleoverflow.styleoverflow.services;

import br.com.styleoverflow.styleoverflow.ConnectionFactory;
import br.com.styleoverflow.styleoverflow.DTO.ProductDTO;
import br.com.styleoverflow.styleoverflow.classes.Product;
import br.com.styleoverflow.styleoverflow.dao.ProductDAO;
import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Size;

import java.sql.Connection;
import java.util.List;

public class ProductService {

    private ConnectionFactory factory;

    public ProductService(ConnectionFactory factory) {
        this.factory = new ConnectionFactory();
    }

    public void createProduct(String name, Size size, Gender gender, String color, Integer stock, Double price) {
        Connection connection = factory.getConnection();

        // implementar tratamentos de erro

        new ProductDAO(connection).createProduct(new ProductDTO(name, size, price, gender, color, stock));

    }

    public List<Product> getAllProducts() {
        Connection connection = factory.getConnection();

        return new ProductDAO(connection).getAllProducts();
    }

    public Product getProductById(int productId) {
        Connection connection = factory.getConnection();

        return new ProductDAO(connection).getProductById(productId);
    }

    public void updateProduct(String name, Size size, Gender gender, String color, Integer stock, Double price, Integer productId) {
        Connection connection = factory.getConnection();

        // implementar tratamentos de erro

        new ProductDAO(connection).updateProduct(new ProductDTO(name, size, price, gender, color, stock), productId);

    }

    public void deleteProduct(Integer productId) {
        Connection connection = factory.getConnection();

        // implementar tratamento de erro

        new ProductDAO(connection).deleteProduct(productId);
    }
}
