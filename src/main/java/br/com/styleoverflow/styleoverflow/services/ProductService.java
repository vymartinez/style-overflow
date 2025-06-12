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

        ProductDTO productDTO = validateProductData(new ProductDTO(name, size, price, gender, color, stock, photoUrl));

        new ProductDAO(connection).createProduct(productDTO);
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

        ProductDTO productDto = validateProductData(new ProductDTO(name, size, price, gender, color, stock, photoUrl));

        connection = factory.getConnection();
        new ProductDAO(connection).updateProduct(productDto, productId);

    }

    public void deleteProduct(Integer productId) {
        Connection connection = factory.getConnection();

        Product product = new ProductDAO(connection).getProductById(productId);

        if (product == null) throw new DomainException("O produto não existe.");

        connection = factory.getConnection();
        new ProductDAO(connection).deleteProduct(productId);
    }

    private ProductDTO validateProductData(ProductDTO productDto) {
        if (productDto.name().isEmpty()) throw new DomainException("O produto deve ter um nome.");

        if (productDto.color().isEmpty()) throw new DomainException("O produto deve ter uma cor.");

        if (productDto.stock() < 0) throw new DomainException("O produto deve ter um quantidade maior ou igual a zero.");

        if (productDto.price() <= 0) throw new DomainException("O produto deve ter um valor maior que zero.");

        return productDto;
    }
}
