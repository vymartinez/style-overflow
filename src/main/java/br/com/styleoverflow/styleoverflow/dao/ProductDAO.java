package br.com.styleoverflow.styleoverflow.dao;

import br.com.styleoverflow.styleoverflow.DTO.ProductDTO;
import br.com.styleoverflow.styleoverflow.DomainException;
import br.com.styleoverflow.styleoverflow.classes.Product;
import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Size;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private Connection connection;

    public ProductDAO(Connection connection) {
        this.connection = connection;
    }

    public void createProduct(ProductDTO productDto) {

        String query = "INSERT INTO products (name, size, gender, color, stock, price) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, productDto.name());
            statement.setString(2, productDto.size().toString());
            statement.setString(3, productDto.gender().toString());
            statement.setString(4, productDto.color());
            statement.setInt(5, productDto.stock());
            statement.setDouble(6, productDto.price());
            statement.execute();

            statement.close();
            connection.close();

        } catch (Exception e) {
            throw new DomainException("erro interno ao criar produto.");
        }
    }

    public Product getProductById(int productId) {

        String query = "SELECT * FROM products WHERE id = ? AND deleted = false";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String size = resultSet.getString("size");
                String gender = resultSet.getString("gender");
                String color = resultSet.getString("color");
                Integer stock = resultSet.getInt("stock");
                Double price = resultSet.getDouble("price");

                return new Product(id, name, Size.valueOf(size), price, Gender.valueOf(gender), color, stock);
            }
        } catch (Exception e) {
            throw new DomainException("erro interno ao buscar produto.");
        }

        return null;
    }

    public List<Product> getAllProducts() {

        String query = "SELECT * FROM products WHERE deleted = false";
        List<Product> products = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String size = resultSet.getString("size");
                String gender = resultSet.getString("gender");
                String color = resultSet.getString("color");
                Integer stock = resultSet.getInt("stock");
                Double price = resultSet.getDouble("price");

                products.add(new Product(id, name, Size.valueOf(size), price, Gender.valueOf(gender), color, stock));
            }
        } catch (Exception e) {
            throw new DomainException("erro interno ao buscar produtos.");
        }

        return products;
    }

    public void updateProduct(ProductDTO productDto, Integer productId) {

        String query = "UPDATE products SET name = ?, size = ?, gender = ?, color = ?, stock = ?, price = ? WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, productDto.name());
            statement.setString(2, productDto.size().toString());
            statement.setString(3, productDto.gender().toString());
            statement.setString(4, productDto.color());
            statement.setInt(5, productDto.stock());
            statement.setDouble(6, productDto.price());
            statement.setInt(7, productId);
            statement.execute();

            statement.close();
            connection.close();

        } catch (Exception e) {
            throw new DomainException("erro interno ao atualizar produto.");
        }
    }

    public void deleteProduct(Integer productId) {

        String query = "UPDATE products SET deleted = true WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productId);
            statement.execute();

            statement.close();
            connection.close();

        } catch (Exception e) {
            throw new DomainException("erro interno ao deletar produto.");
        }
    }

}
