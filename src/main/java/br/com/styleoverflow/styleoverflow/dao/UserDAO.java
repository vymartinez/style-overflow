package br.com.styleoverflow.styleoverflow.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.styleoverflow.styleoverflow.DTO.CreateUserDTO;
import br.com.styleoverflow.styleoverflow.DTO.UpdateUserDTO;
import br.com.styleoverflow.styleoverflow.DomainException;
import br.com.styleoverflow.styleoverflow.classes.Admin;
import br.com.styleoverflow.styleoverflow.interfaces.BaseDAO;
import br.com.styleoverflow.styleoverflow.classes.User;
import br.com.styleoverflow.styleoverflow.enums.Gender;
import br.com.styleoverflow.styleoverflow.enums.Role;

public class UserDAO implements BaseDAO {

    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Record dto) {
        if (!(dto instanceof CreateUserDTO userDto)) throw new RuntimeException("Erro interno. Tente novamente mais tarde.");

        String query = "INSERT INTO users (name, password, gender, address, email, role, cellphone, cep, cpf) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userDto.name());
            statement.setString(2, userDto.password());
            statement.setString(3, userDto.gender().toString());
            statement.setString(4, userDto.address());
            statement.setString(5, userDto.email());
            statement.setString(6, Role.CLIENT.toString());
            statement.setString(7, userDto.cellphone());
            statement.setString(8, userDto.cep());
            statement.setString(9, userDto.cpf());
            statement.execute();

            statement.close();
            connection.close();

        }catch (Exception e) {
            throw new DomainException("erro interno ao criar usuário.");
        }
    }

    public User getById(Integer userId) {

        String query = "SELECT * FROM users WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String gender = resultSet.getString("gender");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                String cellphone = resultSet.getString("cellphone");
                String cep = resultSet.getString("cep");
                String cpf = resultSet.getString("cpf");

                statement.close();
                resultSet.close();
                connection.close();

                return new User(id, name, email, password, cellphone, cpf, cep, address, Gender.valueOf(gender), Role.valueOf(role), new ArrayList<>());
            }
        } catch (Exception e) {
            throw new DomainException("erro interno ao buscar usuário.");
        }

        return null;
    }

    public User getByEmail(String userEmail) {

        String query = "SELECT * FROM users WHERE email = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userEmail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String gender = resultSet.getString("gender");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                String cellphone = resultSet.getString("cellphone");
                String cep = resultSet.getString("cep");
                String cpf = resultSet.getString("cpf");

                statement.close();
                resultSet.close();
                connection.close();

                return new User(id, name, email, password, cellphone, cpf, cep, address, Gender.valueOf(gender), Role.valueOf(role), new ArrayList<>());
            }
        } catch (Exception e) {
            throw new DomainException("erro interno ao buscar usuário.");
        }

        return null;
    }


    @Override
    public void update(Record dto, Integer id) {
        if (!(dto instanceof UpdateUserDTO userDto)) throw new RuntimeException("Erro interno. Tente novamente mais tarde.");

        String query = "UPDATE users SET email = ?, password = ?, cellphone = ?, cep = ?, address = ? WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userDto.email());
            statement.setString(2, userDto.password());
            statement.setString(3, userDto.cellphone());
            statement.setString(4, userDto.cep());
            statement.setString(5, userDto.address());
            statement.setInt(6, id);
            statement.execute();

            statement.close();
            connection.close();

        } catch (Exception e) {
            throw new DomainException("erro interno ao atualizar usuário.");
        }
    }

    public void deleteUser(int userId) {
        String query = "DELETE FROM users WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.execute();

            statement.close();
            connection.close();

        } catch (Exception e) {
            throw new DomainException("Erro interno ao excluir usuário.");
        }
    }
}
